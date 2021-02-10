package com.tycz.tweedle.lib.tweets.stream

import com.tycz.tweedle.lib.api.Response
import com.tycz.tweedle.lib.api.TwitterClient
import com.tycz.tweedle.lib.dtos.tweet.SingleTweetPayload
import com.tycz.tweedle.lib.dtos.tweet.rules.*
import io.ktor.client.call.*
import io.ktor.utils.io.*
import io.ktor.utils.io.core.*
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class TweetsStream {

    private val _client = TwitterClient.instance
    private var _streamChannel: ByteReadChannel? = null

    /**
     * Adds a rule or multiple rules to filter streaming tweets by.
     * Rules need to be added before you start streaming tweets
     *
     * @param token Authorization token
     *
     * @return The response of the request for the rule add
     */
    suspend fun addRules(token: String, rule: Rule):Response<RuleResponse?> {
        return try {
            val url =
                "${TwitterClient.BASE_URL}${TwitterClient.TWEETS_ENDPOINT}/search/stream/rules"
            val response = _client.post<RuleResponse>(token, url, rule)
            Response.Success(response)
        } catch (e: Exception) {
            Response.Error(e)
        }
    }

    /**
     * Deletes a previously added rule
     *
     * @param token Authorization token
     * @param deleteRule Rules to be deleted
     *
     * @return The response of the request to delete a rule or multiple rules
     */
    suspend fun deleteRule(token: String, deleteRule: DeleteRule):Response<DeleteRuleResponse?>{
        return try {
            val url =
                "${TwitterClient.BASE_URL}${TwitterClient.TWEETS_ENDPOINT}/search/stream/rules"
            val response = _client.post<DeleteRuleResponse>(token, url, deleteRule)
            Response.Success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Response.Error(e)
        }
    }

    /**
     * Gets all the current rules for a stream
     *
     * @param token Authorization token
     *
     * @return The current rules added
     */
    suspend fun getRules(token: String):Response<StreamRulesResponse?>{
        return try {
            val url =
                "${TwitterClient.BASE_URL}${TwitterClient.TWEETS_ENDPOINT}/search/stream/rules"
            val response = _client.get<StreamRulesResponse>(token, url)
            Response.Success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Response.Error(e)
        }
    }

    /**
     * Starts a request to live stream tweets in real time based on the added rules
     * Every time a tweet or tweets is received it is sent back to the flow collector
     *
     * Make sure to call closeStream() when done streaming
     *
     * Please note it may take some time for tweets to start coming through initially
     *
     * @param token Authorization token
     *
     * @return Returns a flow that emits a tweet when one is received
     */
    @Suppress("BlockingMethodInNonBlockingContext")
    fun startTweetStream(token: String) = flow {
        try {
            closeStream()

            val url = "https://api.twitter.com/2/tweets/search/stream"
            val statement = _client.getStream(token, url)
            statement.execute {
                _streamChannel = it.receive<ByteReadChannel>()
                var concatString: String = ""

                do {
                    val byteBufferSize = 1024
                    val byteBuffer = ByteArray(byteBufferSize)
                    val currentRead = _streamChannel!!.readAvailable(byteBuffer, 0, byteBufferSize)
                    val s = String(byteBuffer)
                    val delimitedTweets = s.split("\r\n").toMutableList()

                    if (concatString.isNotBlank()) {
                        concatString += delimitedTweets[0]
                        delimitedTweets[0] = concatString
                        concatString = ""
                    }

                    if (!delimitedTweets[delimitedTweets.lastIndex].endsWith("}]}")) {
                        concatString = delimitedTweets[delimitedTweets.lastIndex]
                        delimitedTweets.removeAt(delimitedTweets.lastIndex)
                    }

                    parseStreamResponseString(delimitedTweets).forEach { tweet ->
                        emit(Response.Success(tweet))
                    }
                } while (currentRead >= 0)
            }

            closeStream()
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Response.Error(e))
        }
    }

    /**
     * The sampled stream endpoint delivers a roughly 1% random sample of publicly available Tweets in real-time.
     * With it, you can identify and track trends, monitor general sentiment, monitor global events, and much more.
     *
     * Make sure to call closeStream() when done streaming
     *
     * @param token Authorization token
     *
     * @return Returns a flow that emits a tweet when one is received
     */
    @Suppress("BlockingMethodInNonBlockingContext")
    fun startSampledTweetStream(token: String) = flow {
        try {
            closeStream()

            val url = "https://api.twitter.com/2/tweets/sampled/stream"
            val statement = _client.getStream(token, url)
            statement.execute {
                _streamChannel = it.receive<ByteReadChannel>()
                var concatString: String = ""

                do {
                    val byteBufferSize = 1024
                    val byteBuffer = ByteArray(byteBufferSize)
                    val currentRead = _streamChannel!!.readAvailable(byteBuffer, 0, byteBufferSize)
                    val s = String(byteBuffer)
                    val delimitedTweets = s.split("\r\n").toMutableList()

                    if (concatString.isNotBlank()) {
                        concatString += delimitedTweets[0]
                        delimitedTweets[0] = concatString
                        concatString = ""
                    }

                    if (!delimitedTweets[delimitedTweets.lastIndex].endsWith("}]}")) {
                        concatString = delimitedTweets[delimitedTweets.lastIndex]
                        delimitedTweets.removeAt(delimitedTweets.lastIndex)
                    }

                    parseStreamResponseString(delimitedTweets).forEach { tweet ->
                        emit(Response.Success(tweet))
                    }
                } while (currentRead >= 0)
            }
            closeStream()
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Response.Error(e))
        }
    }

    /**
     * Parses a given list of json strings and deserializes them
     */
    private fun parseStreamResponseString(response: List<String>): List<SingleTweetPayload> {

        val tweets: MutableList<SingleTweetPayload> = mutableListOf()
        val format = Json { ignoreUnknownKeys = true }
        response.forEach { tweetData ->
            if (tweetData.isNotEmpty()) {
                try {
                    val convertedPayload: SingleTweetPayload? =
                        format.decodeFromString<SingleTweetPayload>(tweetData)
                    convertedPayload?.let { tweetObject ->
                        tweets.add(tweetObject)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        return tweets
    }

    /**
     * Closes any active streaming request
     *
     * When you cancel the stream you will get back a final response error from the flow saying CancellationException: Channel has been cancelled
     * At this time you know the stream has been closed and you will no longer receive any new tweets from the flow
     */
    fun closeStream() {
        _streamChannel?.cancel()
        _streamChannel = null
    }
}