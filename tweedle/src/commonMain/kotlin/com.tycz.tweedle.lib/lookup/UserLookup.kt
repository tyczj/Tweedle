package com.tycz.tweedle.lib.lookup

import com.tycz.tweedle.lib.api.Response
import com.tycz.tweedle.lib.api.TwitterClient
import com.tycz.tweedle.lib.dtos.tweet.MultipleTweetPayload
import com.tycz.tweedle.lib.dtos.tweet.SingleTweetPayload
import com.tycz.tweedle.lib.dtos.user.Payload
import kotlinx.coroutines.flow.flow

/**
 * Class with various methods of getting a user from the Twitter API
 */
class UserLookup {

    private val _client = TwitterClient.instance

    /**
     * Gets a user by the user id
     * @param token Authorization token
     * @param userId User id of the user
     *
     * @return Returns a user Payload in a Response
     */
    suspend fun getUserById(token:String, userId:Long):Response<Payload?> {

        return try{
            val url = "${TwitterClient.BASE_URL}${TwitterClient.USERS_ENDPOINT}/$userId"
            val payload = _client.get<Payload>(token, url)
            Response.Success(payload)
        }catch (e:Exception){
            Response.Error(e)
        }
    }

    /**
     * Gets multiple users by user ids
     * @param token Authorization token
     * @param userIds List of users ids for the users to get
     *
     * @return Returns a list of user Payload's in a Response
     */
    suspend fun getUsersByIds(token:String, userIds:List<Long>):Response<List<Payload>?> {

        return try{
            val url = "${TwitterClient.BASE_URL}${TwitterClient.USERS_ENDPOINT}?ids=${userIds.joinToString(",","","",100)}"
            val users = _client.get<List<Payload>>(token, url)
            Response.Success(users)
        }catch (e:Exception){
            Response.Error(e)
        }
    }

    /**
     * Gets a user by the username. Example @TwitterDev
     * @param token Authorization token
     * @param username The username of the user to get
     *
     * @return Returns a user Payload in a Response
     */
    suspend fun getUserByUsername(token:String, username:String): Response<Payload?> {

        return try{
            val url = "${TwitterClient.BASE_URL}${TwitterClient.USERS_ENDPOINT}/by/username/$username"
            val user = _client.get<Payload>(token, url)
            Response.Success(user)
        }catch (e:Exception){
            Response.Error(e)
        }
    }

    /**
     * Gets multiple users by their usernames
     * @param token Authorization token
     * @param usernames List of usernames of the users to get
     *
     * @return Returns a list of user Payload's in a Response
     */
    suspend fun getUsersByUsernames(token:String, usernames:List<String>): Response<List<Payload>?>{

        return try{
            val url = "${TwitterClient.BASE_URL}${TwitterClient.USERS_ENDPOINT}/by?${usernames.joinToString(",", "", "", 100)}"
            val users = _client.get<List<Payload>>(token, url)
            Response.Success(users)
        }catch (e:Exception){
            Response.Error(e)
        }
    }
}