package com.tycz.tweedle.lib.user

import com.tycz.tweedle.lib.ExperimentalApi
import com.tycz.tweedle.lib.api.Response
import com.tycz.tweedle.lib.api.TwitterClient
import com.tycz.tweedle.lib.authentication.SignatureBuilder
import com.tycz.tweedle.lib.authentication.oauth.IOAuthBuilder
import com.tycz.tweedle.lib.authentication.oauth.OAuth1
import com.tycz.tweedle.lib.dtos.user.blocks.BlockedPayload
import com.tycz.tweedle.lib.dtos.user.blocks.BlockingPayload
import com.tycz.tweedle.lib.urlEncodeString
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

class UserBlocks(private val oAuthBuilder: IOAuthBuilder) {

    private val _client = TwitterClient.instance

    @OptIn(ExperimentalApi::class)
    suspend fun getBlockedUsers(userId: Long): Response<BlockedPayload?> {
        return try {
            val urlBuilder = StringBuilder()
            val url = "${TwitterClient.BASE_URL}${TwitterClient.USERS_ENDPOINT}/$userId/blocking"
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
            val payload = _client.get<BlockedPayload>(builder)
            Response.Success(payload)
        }catch (e: Exception){
            Response.Error(e)
        }
    }

    /**
     * Block user
     *
     * Note this currently only works with OAuth2 bearer token authentication. If trying to use OAuth1 an exception will be thrown
     *
     * @see com.tycz.tweedle.lib.authentication.Authentication2 usage to perform actions on behalf of a user
     * @param userId User ID to perform the block on behalf of
     * @param userIdToBlock User ID to block
     * @return
     */
    @OptIn(ExperimentalApi::class)
    suspend fun blockUser(userId: Long, userIdToBlock: Long): Response<BlockingPayload?> {
        return try {
            val url = "${TwitterClient.BASE_URL}${TwitterClient.USERS_ENDPOINT}/$userId/blocking"

            if(oAuthBuilder is OAuth1){
                throw IllegalStateException("This method currently only works with OAuth2")
            }
            val builder = oAuthBuilder.buildRequest()
            builder.url(URLBuilder(url).build())
            builder.contentType(ContentType.Application.Json)
            builder.body = JsonObject(mapOf("target_user_id" to JsonPrimitive(userIdToBlock.toString())))
            val response = _client.put<BlockingPayload>(builder)
            Response.Success(response)
        }catch (e: Exception){
            Response.Error(e)
        }
    }

    /**
     * Unblock user
     *
     * Note this currently only works with OAuth2 bearer token authentication. If trying to use OAuth1 an exception will be thrown
     *
     * @see com.tycz.tweedle.lib.authentication.Authentication2 usage to perform actions on behalf of a user
     * @param userId User ID to perform the unblock on behalf of
     * @param userIdToUnblock User ID to unblock
     * @return
     */
    @OptIn(ExperimentalApi::class)
    suspend fun unblockUser(userId: Long, userIdToUnblock: Long): Response<BlockingPayload?>{
        return try {
            val url = "${TwitterClient.BASE_URL}${TwitterClient.USERS_ENDPOINT}/$userId/blocking/$userIdToUnblock"

            if(oAuthBuilder is OAuth1){
                throw IllegalStateException("This method currently only works with OAuth2")
            }

            val builder = oAuthBuilder.buildRequest()
            builder.url(URLBuilder(url).build())
            val response = _client.put<BlockingPayload>(builder)
            Response.Success(response)
        }catch (e: Exception){
            Response.Error(e)
        }
    }
}