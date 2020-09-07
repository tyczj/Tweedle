package com.tycz.tweedle.lib.tweets.stream

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.tycz.tweedle.lib.api.Response
import com.tycz.tweedle.lib.api.TwitterApi
import com.tycz.tweedle.lib.api.interfaces.ITweetStream
import com.tycz.tweedle.lib.dtos.tweet.rules.Rule
import com.tycz.tweedle.lib.dtos.tweet.SingleTweetPayload
import com.tycz.tweedle.lib.dtos.tweet.rules.DeleteRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.Request
import okio.Buffer
import java.nio.charset.Charset

/**
 * Class with various methods to stream real time tweets
 */
class TweetsStream {

    private val _stream = TwitterApi.httpClient.create(ITweetStream::class.java)
    private var _streamResponse: okhttp3.Response? = null
    private val _moshi = Moshi.Builder().build()
    private val _jsonAdapter: JsonAdapter<SingleTweetPayload> = _moshi.adapter(SingleTweetPayload::class.java)

    /**
     * Adds a rule or multiple rules to filter streaming tweets by.
     * Rules need to be added before you start streaming tweets
     *
     * @param token Authorization token
     *
     * @return The response of the request for the rule add
     */
    fun addRules(token: String, rule: Rule) = flow {
        val result = try {
            val response = _stream.addStreamRules("Bearer $token", rule)
            Response.Success(response)
        } catch (e: Exception) {
            Response.Error(e)
        }
        emit(result)
    }

    /**
     * Deletes a previously added rule
     *
     * @param token Authorization token
     * @param deleteRule Rules to be deleted
     *
     * @return The response of the request to delete a rule or multiple rules
     */
    fun deleteRule(token: String, deleteRule: DeleteRule) = flow {
        val result = try{
            val response = _stream.deleteRuleByRuleValue("Bearer $token", deleteRule)
            Response.Success(response)
        }catch (e:Exception){
            e.printStackTrace()
            Response.Error(e)
        }
        emit(result)
    }

    /**
     * Gets all the current rules for a stream
     *
     * @param token Authorization token
     *
     * @return The current rules added
     */
    fun getRules(token: String) = flow {
        val result = try{
            val response = _stream.getStreamRules("Bearer $token")
            Response.Success(response)
        }catch (e:Exception){
            e.printStackTrace()
            Response.Error(e)
        }
        emit(result)
    }

    /**
     * Starts a request to live stream tweets in real time based on the added rules
     * Every time a tweets is received it is sent back to the flow collector
     *
     * @param token Authorization token
     *
     * @return Returns a flow that emits a tweet when one is received
     */
    @Suppress("BlockingMethodInNonBlockingContext")
    fun startTweetStream(token: String) = flow {
        try{
            val request: Request = Request.Builder()
                .url("https://api.twitter.com/2/tweets/search/stream")
                .addHeader("Authorization", "Bearer $token")
                .method("GET", null)
                .build()
            _streamResponse = TwitterApi.okHttpClient.newCall(request).execute()
            val source = _streamResponse!!.body?.source()
            val buffer = Buffer()
            while(!source!!.exhausted()){
                _streamResponse!!.body?.source()?.read(buffer, 1024)
                val data = buffer.readString(Charset.defaultCharset())
                parseStreamResponseString(data).forEach {
                    emit(Response.Success(it))
                }
            }
            _streamResponse!!.close()
        }catch (e:Exception){
            e.printStackTrace()
            emit(Response.Error(e))
        }
    }.flowOn(Dispatchers.IO)

    @Suppress("BlockingMethodInNonBlockingContext")
    fun startSampledTweetStream(token:String) = flow{
        try{
            closeStream()
            val request: Request = Request.Builder()
                .url("https://api.twitter.com/2/tweets/sampled/stream")
                .addHeader("Authorization", "Bearer $token")
                .method("GET", null)
                .build()
            _streamResponse = TwitterApi.okHttpClient.newCall(request).execute()
            val source = _streamResponse!!.body?.source()
            val buffer = Buffer()
            while(!source!!.exhausted()){
                _streamResponse!!.body?.source()?.read(buffer, 1024)
                val data = buffer.readString(Charset.defaultCharset())
                parseStreamResponseString(data).forEach {
                    emit(Response.Success(it))
                }
            }
            _streamResponse!!.close()
        }catch (e:Exception){
            e.printStackTrace()
            emit(Response.Error(e))
        }
    }.flowOn(Dispatchers.IO)

    /**
     * The sampled stream endpoint delivers a roughly 1% random sample of publicly available Tweets in real-time.
     * With it, you can identify and track trends, monitor general sentiment, monitor global events, and much more.
     */
    private fun parseStreamResponseString(response:String):List<SingleTweetPayload>{
        val delimitedTweets = response.split("\r\n")

        if(delimitedTweets.size > 1){
            delimitedTweets.toMutableList().removeAt(delimitedTweets.lastIndex)
        }

        val tweets:MutableList<SingleTweetPayload> = mutableListOf()
        delimitedTweets.forEach {tweetData ->
            if(tweetData.isNotEmpty()){
                try{
                    val convertedPayload: SingleTweetPayload? = _jsonAdapter.fromJson(tweetData)
                    convertedPayload?.let {tweetObject ->
                        tweets.add(tweetObject)
                    }
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }
        }

        return tweets
    }

    /**
     * Closes any active streaming request
     */
    fun closeStream(){
        _streamResponse?.close()
    }
}