package com.tycz.tweedle.lib.tweets.lookup

import com.tycz.tweedle.lib.api.Response
import com.tycz.tweedle.lib.api.TwitterClient
import com.tycz.tweedle.lib.authentication.SignatureBuilder
import com.tycz.tweedle.lib.authentication.oauth.IOAuthBuilder
import com.tycz.tweedle.lib.authentication.oauth.OAuth1
import com.tycz.tweedle.lib.dtos.tweet.MultipleTweetPayload
import com.tycz.tweedle.lib.dtos.tweet.SingleTweetPayload
import com.tycz.tweedle.lib.urlEncodeString
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

/**
 * Class used to query tweets in the Twitter API
 *
 * @property oAuthBuilder The authentication method to be used to make API calls
 */
class TweetsLookup(private val oAuthBuilder: IOAuthBuilder) {

    private val _client = TwitterClient.instance

    /**
     * Gets a single tweet by the tweet id
     * @param tweetId The if of the tweet to get
     *
     * @return Returns a SingleTweetPayload in a Response
     *
     * @see com.tycz.tweedle.lib.dtos.tweet.SingleTweetPayload
     * @see com.tycz.tweedle.lib.api.Response
     */
    suspend fun getTweet(tweetId:Long): Response<SingleTweetPayload?>{

        return try{
            val url = "${TwitterClient.BASE_URL}${TwitterClient.TWEETS_ENDPOINT}/$tweetId"

            if(oAuthBuilder is OAuth1){
                oAuthBuilder.httpMethod = SignatureBuilder.HTTP_GET
                oAuthBuilder.url = url
            }

            val builder = oAuthBuilder.buildRequest()
            builder.url(URLBuilder(url).build())
            val payload = _client.get<SingleTweetPayload>(builder)
            Response.Success(payload)
        }catch (e:Exception){
            Response.Error(e)
        }
    }

    /**
     * Gets a lost of tweets for the given tweet ids
     * @param tweetIds List of tweet id's to get the tweets for
     *
     * @return Returns a list of MultipleTweetPayload's in a Response
     *
     * @see com.tycz.tweedle.lib.dtos.tweet.MultipleTweetPayload
     * @see com.tycz.tweedle.lib.api.Response
     */
    suspend fun getMultipleTweets(tweetIds:List<Long>): Response<MultipleTweetPayload?> {

        return try{
            val url = "${TwitterClient.BASE_URL}${TwitterClient.TWEETS_ENDPOINT}"
            val urlBuilder = StringBuilder()
            urlBuilder.append(url)

            val parameters = HashMap<String, String>()
            parameters["ids"] = tweetIds.joinToString(",","","",100)

            if(oAuthBuilder is OAuth1){
                oAuthBuilder.httpMethod = SignatureBuilder.HTTP_GET
                oAuthBuilder.url = url
                oAuthBuilder.parameters = parameters
            }

            urlBuilder.append("?")
            parameters.onEachIndexed{index, entry ->
                urlBuilder.append("${entry.key}=${urlEncodeString(entry.value)}")
                if(index < parameters.size-1){
                    urlBuilder.append("&")
                }
            }

            val builder = oAuthBuilder.buildRequest()
            builder.url(URLBuilder(urlBuilder.toString()).build())
            val tweets = _client.get<MultipleTweetPayload>(builder)
            Response.Success(tweets)
        }catch (e:Exception){
            Response.Error(e)
        }
    }

    /**
     * Hide tweet
     *
     * Note this currently only works with OAuth2 bearer token authentication. If trying to use OAuth1 an exception will be thrown
     * @see com.tycz.tweedle.lib.authentication.Authentication2 usage to perform actions on behalf of a user
     * @throws IllegalStateException when called with OAuth1 scope
     * @param tweetId ID of the tweet to hide
     * @return
     */
    @OptIn(ExperimentalSerializationApi::class)
    suspend fun hideTweet(tweetId:Long):Response<HttpResponse?> {
        return try{
            val url = "${TwitterClient.BASE_URL}${TwitterClient.TWEETS_ENDPOINT}/$tweetId/hidden"
            if(oAuthBuilder is OAuth1){
                throw IllegalStateException("This method currently only works with OAuth2")
            }
            val builder = oAuthBuilder.buildRequest()
            builder.url(URLBuilder(url).build())
            builder.contentType(ContentType.Application.Json)
            builder.body = JsonObject(mapOf("hidden" to JsonPrimitive(true)))
            val response = _client.put<HttpResponse>(builder)
            Response.Success(response)
        }catch (e:Exception){
            Response.Error(e)
        }
    }

    /**
     * Unhide tweet
     *
     * Note this currently only works with OAuth2 bearer token authentication. If trying to use OAuth1 an exception will be thrown
     * @see com.tycz.tweedle.lib.authentication.Authentication2 usage to perform actions on behalf of a user
     * @throws IllegalStateException when called with OAuth1 scope
     * @param tweetId ID of the tweet to unhide
     * @return
     */
    @OptIn(ExperimentalSerializationApi::class)
    suspend fun unhideTweet(tweetId:Long):Response<HttpResponse?> {
        return try{
            val url = "${TwitterClient.BASE_URL}${TwitterClient.TWEETS_ENDPOINT}/$tweetId/hidden"
            if(oAuthBuilder is OAuth1){
                throw IllegalStateException("This method currently only works with OAuth2")
            }
            val builder = oAuthBuilder.buildRequest()
            builder.url(URLBuilder(url).build())
            builder.contentType(ContentType.Application.Json)
            builder.body = JsonObject(mapOf("hidden" to JsonPrimitive(false)))
            val response = _client.put<HttpResponse>(builder)
            Response.Success(response)
        }catch (e:Exception){
            Response.Error(e)
        }
    }

    /**
     * Gets a list of tweets posted over the last week
     * @see <a href="https://developer.twitter.com/en/docs/twitter-api/tweets/search/introduction">Recent Search</a>
     *
     * @param query Search query to submit to the recent search endpoint
     * @param additionalParameters Additional fields to be returned for extra information not in the query
     *
     * @return Returns a MultipleTweetPayload in a Response
     *
     * @see com.tycz.tweedle.lib.dtos.tweet.MultipleTweetPayload
     * @see com.tycz.tweedle.lib.api.Response
     */
    suspend fun getRecentTweets(query:String, additionalParameters:Map<String,String>):Response<MultipleTweetPayload?> {

        return try{
            val urlBuilder = StringBuilder()

            val url = "${TwitterClient.BASE_URL}${TwitterClient.TWEETS_ENDPOINT}/search/recent"
            urlBuilder.append(url)

            val parameters = HashMap<String, String>()
            parameters["query"] = query

            additionalParameters.forEach {
                parameters[it.key] = it.value
            }

            if(oAuthBuilder is OAuth1){
                oAuthBuilder.httpMethod = SignatureBuilder.HTTP_GET
                oAuthBuilder.url = urlBuilder.toString()
                oAuthBuilder.parameters = parameters
            }

            urlBuilder.append("?")
            parameters.onEachIndexed{index, entry ->
                urlBuilder.append("${entry.key}=${urlEncodeString(entry.value)}")
                if(index < parameters.size-1){
                    urlBuilder.append("&")
                }
            }

            val builder = oAuthBuilder.buildRequest()
            builder.url(URLBuilder(urlBuilder.toString()).build())

            val response = _client.get<MultipleTweetPayload>(builder)
            Response.Success(response)
        }catch (e:Exception){
            Response.Error(e)
        }
    }

    /**
     * Gets all the quotes of a specific tweet
     * @see <a href="https://developer.twitter.com/en/docs/twitter-api/tweets/quote-tweets/api-reference/get-tweets-id-quote_tweets">Quotes</a>
     *
     * @param tweetId ID of the tweet to get all the quotes for
     * @param additionalParameters Additional fields to be returned for extra information not in the query
     * @return Returns a MultipleTweetPayload in a Response
     *
     * @see com.tycz.tweedle.lib.dtos.tweet.MultipleTweetPayload
     * @see com.tycz.tweedle.lib.api.Response
     */
    suspend fun getQuotesForTweet(tweetId: Long, additionalParameters:Map<String,String> = mapOf()):Response<MultipleTweetPayload?>{
        return try {
            val urlBuilder = StringBuilder()

            val url = "${TwitterClient.BASE_URL}${TwitterClient.TWEETS_ENDPOINT}/$tweetId/quote_tweets"
            urlBuilder.append(url)

            val parameters = HashMap<String, String>()

            additionalParameters.forEach {
                parameters[it.key] = it.value
            }

            if(oAuthBuilder is OAuth1){
                oAuthBuilder.httpMethod = SignatureBuilder.HTTP_GET
                oAuthBuilder.url = urlBuilder.toString()
                oAuthBuilder.parameters = parameters
            }

            if(parameters.isNotEmpty()){
                urlBuilder.append("?")
                parameters.onEachIndexed{index, entry ->
                    urlBuilder.append("${entry.key}=${urlEncodeString(entry.value)}")
                    if(index < parameters.size-1){
                        urlBuilder.append("&")
                    }
                }
            }

            val builder = oAuthBuilder.buildRequest()
            builder.url(URLBuilder(urlBuilder.toString()).build())

            val response = _client.get<MultipleTweetPayload>(builder)
            Response.Success(response)
        }catch (e:Exception){
            Response.Error(e)
        }
    }
}