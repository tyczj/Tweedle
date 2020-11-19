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
     * @return Returns a flow with the response and the payload
     */
    fun getUserById(token:String, userId:Long) = flow {

        val result = try{
            val url = "${TwitterClient.BASE_URL}${TwitterClient.USERS_ENDPOINT}/$userId"
            val payload = _client.get<Payload>(token, url)
            Response.Success(payload)
        }catch (e:Exception){
            Response.Error(e)
        }

        emit(result)
    }

    /**
     * Gets multiple users by user ids
     * @param token Authorization token
     * @param userIds List of users ids for the users to get
     *
     * @return Returns a flow with the response and its payload
     */
    fun getUsersByIds(token:String, userIds:List<Long>) = flow {

        val result = try{
            val url = "${TwitterClient.BASE_URL}${TwitterClient.USERS_ENDPOINT}?ids=${userIds.joinToString(",","","",100)}"
            val users = _client.get<List<Payload>>(token, url)
            Response.Success(users)
        }catch (e:Exception){
            Response.Error(e)
        }

        emit(result)
    }

    /**
     * Gets a user by the username. Example @TwitterDev
     * @param token Authorization token
     * @param username The username of the user to get
     *
     * @return Returns a flow with the response and its payload
     */
    fun getUserByUsername(token:String, username:String) = flow {

        val result = try{
            val url = "${TwitterClient.BASE_URL}${TwitterClient.USERS_ENDPOINT}/by/username/$username"
            val user = _client.get<Payload>(token, url)
            Response.Success(user)
        }catch (e:Exception){
            Response.Error(e)
        }

        emit(result)
    }

    /**
     * Gets multiple users by their usernames
     * @param token Authorization token
     * @param usernames List of usernames of the users to get
     *
     * @return Returns a flow with the response and its payload
     */
    fun getUsersByUsernames(token:String, usernames:List<String>) = flow {

        val result = try{
            val url = "${TwitterClient.BASE_URL}${TwitterClient.USERS_ENDPOINT}/by?${usernames.joinToString(",", "", "", 100)}"
            val users = _client.get<List<Payload>>(token, url)
            Response.Success(users)
        }catch (e:Exception){
            Response.Error(e)
        }

        emit(result)
    }
}