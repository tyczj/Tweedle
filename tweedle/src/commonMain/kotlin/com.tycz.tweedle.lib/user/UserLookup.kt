package com.tycz.tweedle.lib.user

import com.tycz.tweedle.lib.ExperimentalApi
import com.tycz.tweedle.lib.api.Response
import com.tycz.tweedle.lib.api.TwitterClient
import com.tycz.tweedle.lib.authentication.SignatureBuilder
import com.tycz.tweedle.lib.authentication.oauth.IOAuthBuilder
import com.tycz.tweedle.lib.authentication.oauth.OAuth1
import com.tycz.tweedle.lib.dtos.user.Payload
import com.tycz.tweedle.lib.dtos.user.UserPayload
import com.tycz.tweedle.lib.urlEncodeString
import io.ktor.client.request.*
import io.ktor.http.*

/**
 * Class with various methods of getting a user from the Twitter API
 */
class UserLookup(private val oAuthBuilder: IOAuthBuilder) {

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
}