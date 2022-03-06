package com.tycz.tweedle.lib.user

import com.tycz.tweedle.lib.ExperimentalApi
import com.tycz.tweedle.lib.api.Response
import com.tycz.tweedle.lib.api.TwitterClient
import com.tycz.tweedle.lib.authentication.SignatureBuilder
import com.tycz.tweedle.lib.authentication.oauth.IOAuthBuilder
import com.tycz.tweedle.lib.authentication.oauth.OAuth1
import com.tycz.tweedle.lib.dtos.tweet.MultipleTweetPayload
import com.tycz.tweedle.lib.dtos.user.Payload
import com.tycz.tweedle.lib.dtos.user.UserPayload
import com.tycz.tweedle.lib.dtos.user.blocks.BlockedPayload
import com.tycz.tweedle.lib.dtos.user.blocks.BlockingPayload
import com.tycz.tweedle.lib.dtos.user.follows.FollowersPayload
import com.tycz.tweedle.lib.dtos.user.follows.FollowingPayload
import com.tycz.tweedle.lib.dtos.user.muting.MutedPayload
import com.tycz.tweedle.lib.dtos.user.muting.MutingPayload
import com.tycz.tweedle.lib.urlEncodeString
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

/**
 * Class with various methods of getting a user from the Twitter API
 */
class UserEndpoints(private val oAuthBuilder: IOAuthBuilder) {

    private val _client = TwitterClient.instance

    /**
     * Gets a user by the user id
     * @param userId User id of the user
     *
     * @return Returns a user Payload in a Response
     */
    @OptIn(ExperimentalApi::class)
    suspend fun getUserById(userId:Long, additionalParameters:Map<String,String> = mapOf()):Response<UserPayload?> {

        return try{
            val urlBuilder = StringBuilder()

            val url = "${TwitterClient.BASE_URL}${TwitterClient.USERS_ENDPOINT}/$userId"
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
            val payload = _client.get<UserPayload>(builder)
            Response.Success(payload)
        }catch (e:Exception){
            Response.Error(e)
        }
    }

    /**
     * Gets a users timeline
     *
     * @param userId User to get the tweet timeline for
     * @param additionalParameters Parameters for the query and data returned
     */
    @OptIn(ExperimentalApi::class)
    suspend fun getUserTimeline(userId:Long, additionalParameters:Map<String,String> = mapOf()): Response<MultipleTweetPayload?>{
        return try{
            val urlBuilder = StringBuilder()

            val url = "${TwitterClient.BASE_URL}${TwitterClient.USERS_ENDPOINT}/$userId/tweets"
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
     * Gets a users mentions
     *
     * @param userId User to get all the mentions for
     * @param additionalParameters Parameters for the query and data returned
     */
    @OptIn(ExperimentalApi::class)
    suspend fun getUserMentions(userId:Long, additionalParameters:Map<String,String> = mapOf()): Response<MultipleTweetPayload?>{
        return try{
            val urlBuilder = StringBuilder()

            val url = "${TwitterClient.BASE_URL}${TwitterClient.USERS_ENDPOINT}/$userId/mentions"
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
     * Gets multiple users by user ids
     * @param userIds List of users ids for the users to get
     *
     * @return Returns a list of user Payload's in a Response
     */
    @OptIn(ExperimentalApi::class)
    suspend fun getUsersByIds(userIds:List<Long>, additionalParameters:Map<String,String> = mapOf()):Response<Payload?> {

        return try{
            val urlBuilder = StringBuilder()

            val url = "${TwitterClient.BASE_URL}${TwitterClient.USERS_ENDPOINT}"
            urlBuilder.append(url)

            val parameters = HashMap<String, String>()
            parameters["ids"] = userIds.joinToString(",","","",100)

            additionalParameters.forEach {
                parameters[it.key] = it.value
            }

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
            val users = _client.get<Payload>(builder)
            Response.Success(users)
        }catch (e:Exception){
            Response.Error(e)
        }
    }

    /**
     * Gets a user by the username. Example @TwitterDev
     * @param username The username of the user to get
     *
     * @return Returns a user Payload in a Response
     */
    @OptIn(ExperimentalApi::class)
    suspend fun getUserByUsername(username:String, additionalParameters:Map<String,String> = mapOf()): Response<UserPayload?> {

        return try{
            val urlBuilder = StringBuilder()
            val url = "${TwitterClient.BASE_URL}${TwitterClient.USERS_ENDPOINT}/by/username/$username"
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
            val user = _client.get<UserPayload>(builder)
            Response.Success(user)
        }catch (e:Exception){
            Response.Error(e)
        }
    }

    /**
     * Gets multiple users by their usernames
     * @param usernames List of usernames of the users to get
     *
     * @return Returns a list of user Payload's in a Response
     */
    @OptIn(ExperimentalApi::class)
    suspend fun getUsersByUsernames(usernames:List<String>, additionalParameters:Map<String,String> = mapOf()): Response<Payload?>{

        return try{
            val urlBuilder = StringBuilder()
            val url = "${TwitterClient.BASE_URL}${TwitterClient.USERS_ENDPOINT}/by"
            urlBuilder.append(url)

            val parameters = HashMap<String, String>()
            parameters["usernames"] = usernames.joinToString(",", "", "", 100)

            additionalParameters.forEach {
                parameters[it.key] = it.value
            }

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
            val users = _client.get<Payload>(builder)
            Response.Success(users)
        }catch (e:Exception){
            Response.Error(e)
        }
    }

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
     * Note this currently only works with OAuth2 bearer token authentication. If trying to use OAuth1 an exception will be thrown
     *
     * @see com.tycz.tweedle.lib.authentication.Authentication2 usage to perform actions on behalf of a user
     * @param userId The user ID who you would like to initiate the mute on behalf of.
     * @param userIdToMute The user ID of the user that you would like to mute
     * @return
     */
    @OptIn(ExperimentalApi::class)
    suspend fun muteUser(userId: Long, userIdToMute: Long): Response<MutedPayload?> {
        return try {
            val url = "${TwitterClient.BASE_URL}${TwitterClient.USERS_ENDPOINT}/$userId/muting"

            if(oAuthBuilder is OAuth1){
                throw IllegalStateException("This method currently only works with OAuth2")
            }
            val builder = oAuthBuilder.buildRequest()
            builder.url(URLBuilder(url).build())
            builder.contentType(ContentType.Application.Json)
            builder.body = JsonObject(mapOf("target_user_id" to JsonPrimitive(userIdToMute.toString())))
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
    suspend fun unmuteUser(userId: Long, userIdToUnmute: Long): Response<MutedPayload?>{
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