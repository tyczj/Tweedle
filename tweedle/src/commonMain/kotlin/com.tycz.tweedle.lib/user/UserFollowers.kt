package com.tycz.tweedle.lib.user

import com.tycz.tweedle.lib.ExperimentalApi
import com.tycz.tweedle.lib.api.Response
import com.tycz.tweedle.lib.api.TwitterClient
import com.tycz.tweedle.lib.authentication.SignatureBuilder
import com.tycz.tweedle.lib.authentication.oauth.IOAuthBuilder
import com.tycz.tweedle.lib.authentication.oauth.OAuth1
import com.tycz.tweedle.lib.dtos.user.follows.FollowersPayload
import com.tycz.tweedle.lib.dtos.user.follows.FollowingPayload
import com.tycz.tweedle.lib.urlEncodeString
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

class UserFollowers(private val oAuthBuilder: IOAuthBuilder) {

    private val _client = TwitterClient.instance

    /**
     * Gets the followers of a user by the given user id
     *
     * @param userId User id to get the followers for
     */
    @OptIn(ExperimentalApi::class)
    suspend fun getFollowers(userId: Long): Response<FollowersPayload?>{
        return try {
            val urlBuilder = StringBuilder()
            val url = "${TwitterClient.BASE_URL}${TwitterClient.USERS_ENDPOINT}/$userId/followers"
            urlBuilder.append(url)

            val parameters = HashMap<String, String>()
            parameters["user.fields"] = "description,profile_image_url"

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
            val user = _client.get<FollowersPayload>(builder)
            Response.Success(user)
        }catch (e: Exception){
            Response.Error(e)
        }
    }

    /**
     * Gets the all the users following the given user id
     *
     * @param userId User id to get all the users following that id
     */
    @OptIn(ExperimentalApi::class)
    suspend fun getFollowing(userId: Long): Response<FollowersPayload?>{
        return try {
            val urlBuilder = StringBuilder()
            val url = "${TwitterClient.BASE_URL}${TwitterClient.USERS_ENDPOINT}/$userId/following"
            urlBuilder.append(url)

            val parameters = HashMap<String, String>()
            parameters["user.fields"] = "description,profile_image_url"

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
            val user = _client.get<FollowersPayload>(builder)
            Response.Success(user)
        }catch (e: Exception){
            Response.Error(e)
        }

    }

    /**
     * Follow user
     *
     * Note this currently only works with OAuth2 bearer token authentication. If trying to use OAuth1 an exception will be thrown
     *
     * @see com.tycz.tweedle.lib.authentication.Authentication2 usage to perform actions on behalf of a user
     * @param userId The authenticated user ID who you would like to initiate the follow on behalf of
     * @param userIdToFollow The user ID of the user that you would like the id to follow.
     */
    @OptIn(ExperimentalApi::class)
    internal suspend fun followUser(userId: Long, userIdToFollow: Long): Response<FollowingPayload?>{
        return try {
            val url = "${TwitterClient.BASE_URL}${TwitterClient.USERS_ENDPOINT}/$userId/following"

            if(oAuthBuilder is OAuth1){
                throw IllegalStateException("This method currently only works with OAuth2")
            }
            val builder = oAuthBuilder.buildRequest()
            builder.url(URLBuilder(url).build())
            builder.contentType(ContentType.Application.Json)
            builder.body = JsonObject(mapOf("target_user_id" to JsonPrimitive(userIdToFollow.toString())))
            val response = _client.post<FollowingPayload>(builder)
            Response.Success(response)
        }catch (e: Exception){
            Response.Error(e)
        }
    }

    /**
     * Unfollow user
     *
     * Note this currently only works with OAuth2 bearer token authentication. If trying to use OAuth1 an exception will be thrown
     *
     * @see com.tycz.tweedle.lib.authentication.Authentication2 usage to perform actions on behalf of a user
     * @param userId The user ID of the user that you would like the id to follow.
     * @param userIdToUnfollow The user ID of the user that you would like the to unfollow.
     */
    @OptIn(ExperimentalApi::class)
    suspend fun unfollowUser(userId: Long, userIdToUnfollow: Long): Response<FollowingPayload?>{
        return try {
            val url = "${TwitterClient.BASE_URL}${TwitterClient.USERS_ENDPOINT}/$userId/following/$userIdToUnfollow"

            if(oAuthBuilder is OAuth1){
                throw IllegalStateException("This method currently only works with OAuth2")
            }

            val builder = oAuthBuilder.buildRequest()
            builder.url(URLBuilder(url).build())
            val response = _client.delete<FollowingPayload>(builder)
            Response.Success(response)
        }catch (e: Exception){
            Response.Error(e)
        }
    }
}