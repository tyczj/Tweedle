package com.tycz.tweedle.lib.lookup

import com.tycz.tweedle.lib.api.Response
import com.tycz.tweedle.lib.api.TwitterApi
import com.tycz.tweedle.lib.api.interfaces.IUserLookup
import kotlinx.coroutines.flow.flow

/**
 * Class with various methods of getting a user from the Twitter API
 */
class UserLookup {

    private val _lookup = TwitterApi.httpClient.create(IUserLookup::class.java)

    /**
     * Gets a user by the user id
     * @param token Authorization token
     * @param userId User id of the user
     *
     * @return Returns a flow with the response and the payload
     */
    fun getUserById(token:String, userId:Long) = flow {

        val result = try{
            val user = _lookup.getUser("Bearer $token", userId)
            Response.Success(user)
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
            val users = _lookup.getUsers("Bearer $token", userIds.joinToString(",", "", "", 100))
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
            val user = _lookup.getUserByUsername("Bearer $token", username)
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
            val users = _lookup.getUsersByUsernames("Bearer $token", usernames.joinToString(",", "", "", 100))
            Response.Success(users)
        }catch (e:Exception){
            Response.Error(e)
        }

        emit(result)
    }
}