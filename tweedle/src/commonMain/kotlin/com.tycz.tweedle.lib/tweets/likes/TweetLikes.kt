package com.tycz.tweedle.lib.tweets.likes

import com.tycz.tweedle.lib.api.Response
import com.tycz.tweedle.lib.api.TwitterClient
import com.tycz.tweedle.lib.authentication.SignatureBuilder
import com.tycz.tweedle.lib.authentication.oauth.IOAuthBuilder
import com.tycz.tweedle.lib.authentication.oauth.OAuth1
import com.tycz.tweedle.lib.dtos.tweet.MultipleTweetPayload
import com.tycz.tweedle.lib.dtos.user.likes.LikesPayload
import com.tycz.tweedle.lib.dtos.user.likes.LikingUserPayload
import com.tycz.tweedle.lib.urlEncodeString
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

/**
 * Class used for interfacing with the likes Twitter API
 *
 * @property oAuthBuilder The authentication method to be used to make API calls
 */
class TweetLikes(private val oAuthBuilder: IOAuthBuilder) {

    private val _client = TwitterClient.instance

    /**
     * Gets likes for a tweet
     *
     * @param tweetId Tweet id to get likes for
     * @param additionalParameters Additional fields to be returned for extra information not in the query
     * @return Returns a LikingUserPayload in a Response
     *
     * @see com.tycz.tweedle.lib.dtos.user.likes.LikingUserPayload
     * @see com.tycz.tweedle.lib.api.Response
     */
    suspend fun getLikesForTweet(tweetId: Long, additionalParameters:Map<String,String> = mapOf()): Response<LikingUserPayload?> {
        return try{
            val urlBuilder = StringBuilder()

            val url = "${TwitterClient.BASE_URL}${TwitterClient.TWEETS_ENDPOINT}/$tweetId/liking_users"
            urlBuilder.append(url)

            val parameters = HashMap<String, String>()
            additionalParameters.forEach {
                parameters[it.key] = it.value
            }

            if(oAuthBuilder is OAuth1){
                oAuthBuilder.httpMethod = SignatureBuilder.HTTP_GET
                oAuthBuilder.url = url
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
            val payload = _client.get<LikingUserPayload>(builder)
            Response.Success(payload)
        }catch (e:Exception){
            Response.Error(e)
        }
    }

    /**
     * Gets all a users likes
     *
     * @param userId User id to get likes for
     * @param additionalParameters Additional fields to be returned for extra information not in the query
     *
     * @return Returns a MultipleTweetPayload in a Response
     *
     * @see com.tycz.tweedle.lib.dtos.tweet.MultipleTweetPayload
     * @see com.tycz.tweedle.lib.api.Response
     */
    suspend fun getUserLikedTweets(userId: Long, additionalParameters:Map<String,String> = mapOf()):Response<MultipleTweetPayload?>{
        return try{
            val urlBuilder = StringBuilder()

            val url = "${TwitterClient.BASE_URL}${TwitterClient.USERS_ENDPOINT}/$userId/liked_tweets"
            urlBuilder.append(url)

            val parameters = HashMap<String, String>()
            additionalParameters.forEach {
                parameters[it.key] = it.value
            }

            if(oAuthBuilder is OAuth1){
                oAuthBuilder.httpMethod = SignatureBuilder.HTTP_GET
                oAuthBuilder.url = url
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
            val payload = _client.get<MultipleTweetPayload>(builder)
            Response.Success(payload)
        }catch (e:Exception){
            Response.Error(e)
        }
    }

    /**
     * Likes a tweet
     *
     * @param tweetId Tweet id to like
     * @param userId User id of the user liking the tweet
     *
     * @throws IllegalStateException when called with OAuth1 scope
     *
     * @return Returns a payload on if the tweet was liked or not
     *
     * @see com.tycz.tweedle.lib.dtos.user.likes.LikesPayload
     */
    suspend fun likeTweet(tweetId: Long, userId: Long):Response<LikesPayload?>{
        return try {
            val url = "${TwitterClient.BASE_URL}${TwitterClient.USERS_ENDPOINT}/$userId/likes"

            if(oAuthBuilder is OAuth1){
                throw IllegalStateException("This method currently only works with OAuth2")
            }
            val builder = oAuthBuilder.buildRequest()
            builder.url(URLBuilder(url).build())
            builder.contentType(ContentType.Application.Json)
            builder.body = JsonObject(mapOf("tweet_id" to JsonPrimitive(tweetId.toString())))
            val response = _client.post<LikesPayload>(builder)
            Response.Success(response)
        }catch (e: Exception){
            Response.Error(e)
        }
    }

    /**
     * Unlikes a tweet
     *
     * @param tweetId Tweet id to like
     * @param userId User id of the user liking the tweet
     * @throws IllegalStateException when called with OAuth1 scope
     *
     * @return Returns a payload on if the tweet was liked or not
     *
     * @see com.tycz.tweedle.lib.dtos.user.likes.LikesPayload
     */
    suspend fun unlikeTweet(tweetId: Long, userId: Long):Response<LikesPayload?>{
        return try {
            val url = "${TwitterClient.BASE_URL}${TwitterClient.USERS_ENDPOINT}/$userId/likes/$tweetId"

            if(oAuthBuilder is OAuth1){
                throw IllegalStateException("This method currently only works with OAuth2")
            }

            val builder = oAuthBuilder.buildRequest()
            builder.url(URLBuilder(url).build())
            val response = _client.delete<LikesPayload>(builder)
            Response.Success(response)
        }catch (e: Exception){
            Response.Error(e)
        }
    }
}