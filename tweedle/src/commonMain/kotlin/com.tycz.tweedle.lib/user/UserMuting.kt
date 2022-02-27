package com.tycz.tweedle.lib.user

import com.tycz.tweedle.lib.ExperimentalApi
import com.tycz.tweedle.lib.api.Response
import com.tycz.tweedle.lib.api.TwitterClient
import com.tycz.tweedle.lib.authentication.SignatureBuilder
import com.tycz.tweedle.lib.authentication.oauth.IOAuthBuilder
import com.tycz.tweedle.lib.authentication.oauth.OAuth1
import com.tycz.tweedle.lib.dtos.user.muting.MutedPayload
import com.tycz.tweedle.lib.dtos.user.muting.MutingPayload
import com.tycz.tweedle.lib.urlEncodeString
import io.ktor.client.request.*
import io.ktor.http.*

class UserMuting(private val oAuthBuilder: IOAuthBuilder) {

    private val _client = TwitterClient.instance

    /**
     * Get muted users
     *
     * @param userId The user ID whose muted users you would like to retrieve.
     * @return Returns a list of users who are muted by the specified user ID.
     */
    @OptIn(ExperimentalApi::class)
    suspend fun getMutedUsers(userId: Long): Response<MutingPayload?> {
        return try {
            val urlBuilder = StringBuilder()
            val url = "${TwitterClient.BASE_URL}${TwitterClient.USERS_ENDPOINT}/$userId/muting"
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
            val payload = _client.get<MutingPayload>(builder)
            Response.Success(payload)
        }catch (e: Exception){
            Response.Error(e)
        }
    }

    /**
     * Mute user
     *
     * @param userId The user ID who you would like to initiate the mute on behalf of.
     * @param userIdToMute The user ID of the user that you would like to mute
     * @return
     */
    @OptIn(ExperimentalApi::class)
    internal suspend fun muteUser(userId: Long, userIdToMute: Long): Response<MutedPayload?> {
        //TODO
        return try {
            val url = "${TwitterClient.BASE_URL}${TwitterClient.USERS_ENDPOINT}/$userId/muting"

            if(oAuthBuilder is OAuth1){
                oAuthBuilder.httpMethod = SignatureBuilder.HTTP_POST
                oAuthBuilder.url = url
            }
            val builder = oAuthBuilder.buildRequest()
            builder.url(URLBuilder(url).build())
            builder.contentType(ContentType.Application.Json)
            builder.body = "{\"target_user_id\": \"$userIdToMute\"}"
            val response = _client.put<MutedPayload>(builder)
            Response.Success(response)
        }catch (e: Exception){
            Response.Error(e)
        }
    }

    /**
     * Unmute user
     * @param userId The user ID who you would like to initiate an unmute on behalf of.
     * @param userIdToUnmute The user ID of the user that you would like to unmute.
     * @return
     */
    @OptIn(ExperimentalApi::class)
    internal suspend fun unmuteUser(userId: Long, userIdToUnmute: Long): Response<MutedPayload?>{
        //TODO
        return try {
            val url = "${TwitterClient.BASE_URL}${TwitterClient.USERS_ENDPOINT}/$userId/muting/$userIdToUnmute"

            if(oAuthBuilder is OAuth1){
                oAuthBuilder.httpMethod = SignatureBuilder.HTTP_DELETE
                oAuthBuilder.url = url
            }

            val builder = oAuthBuilder.buildRequest()
            builder.url(URLBuilder(url).build())
            val response = _client.put<MutedPayload>(builder)
            Response.Success(response)
        }catch (e: Exception){
            Response.Error(e)
        }
    }
}