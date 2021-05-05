package com.tycz.tweedle.lib.authentication

import com.tycz.tweedle.lib.ExperimentalApi
import com.tycz.tweedle.lib.api.Response
import com.tycz.tweedle.lib.api.TwitterClient
import com.tycz.tweedle.lib.authentication.oauth.OAuth1
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Class for authenticating with twitter
 */
class Authentication @ExperimentalApi constructor(private val oAuth: OAuth1) {
    private val _client = TwitterClient.instance

    /**
     * Allows a Consumer application to obtain an OAuth Request Token to request user authorization
     * First step in the OAuth 1.0 authentication flow
     *
     * @param callbackUrl The value you specify here will be used as the URL a user is redirected to should they approve your application's access to their account.
     * callback URL used with this endpoint will have to be configured within the App’s settings on developer.twitter.com
     */
    @ExperimentalApi
    suspend fun requestToken(callbackUrl: String):TokenResponse = withContext(Dispatchers.Default){

        oAuth.callbackUrl = callbackUrl
        oAuth.httpMethod = SignatureBuilder.HTTP_POST
        oAuth.url = SignatureBuilder.AUTH_REQUEST_TOKEN_URL

        val builder = oAuth.buildRequest()
        builder.url(URLBuilder("${TwitterClient.AUTH_BASE_URL}${TwitterClient.REQUEST_TOKEN_ENDPOINT}").build())

        val response = try{
            val response = _client.post<String>(builder)
            Response.Success(response)
        }catch (e:Exception){
            Response.Error(e)
        }

        if(response is Response.Success){
            parseTokenResponse(response.data!!)
        }else{
            TokenResponse(null, null, null, (response as Response.Error).exception.message)
        }
    }

    /**
     * Allows a Consumer application to exchange the OAuth Request Token for an OAuth Access Token
     * Final step in obtaining an OAuth access token
     *
     * @param oauthToken Token from step two of the authentication flow, the twitter authorize request
     *
     * @param oauthVerifier Token from step two of the authentication flow, the twitter authorize request
     */
    suspend fun getAccessToken(oauthToken: String, oauthVerifier: String):AccessTokenResponse = withContext(Dispatchers.Default){
        val builder = HttpRequestBuilder()
        builder.url(URLBuilder("${TwitterClient.AUTH_BASE_URL}${TwitterClient.ACCESS_TOKEN_ENDPOINT}?oauth_token=$oauthToken&oauth_verifier=$oauthVerifier").build())

        val response = try{
            val response = _client.post<String>(builder)
            Response.Success(response)
        }catch (e:Exception){
            Response.Error(e)
        }

        if(response is Response.Success){
            parseAccessTokenResponse(response.data!!)
        }else{
            AccessTokenResponse(null, null, null, (response as Response.Error).exception.message)
        }
    }

    /**
     * Parses the response from requestToken request
     */
    private fun parseTokenResponse(response:String):TokenResponse{
        val splitResponse = response.split("&")
        return if(splitResponse.size > 2){
            val oauthToken = splitResponse[0].split("=")[1]
            val oauthTokenSecret = splitResponse[1].split("=")[1]
            val oauthCallbackConfirmed = splitResponse[2].split("=")[1]
            TokenResponse(oauthToken, oauthTokenSecret, oauthCallbackConfirmed=="true", null)
        }else{
            TokenResponse(null, null, null, "Unable to parse request token response: $response")
        }
    }

    /**
     * Parses the response from the access token request
     */
    private fun parseAccessTokenResponse(response:String): AccessTokenResponse{
        val splitResponse = response.split("&")
        return if(splitResponse.size > 2){
            val oauthToken = splitResponse[0].split("=")[1]
            val oauthTokenSecret = splitResponse[1].split("=")[1]
            val screenName = splitResponse[2].split("=")[1]
            AccessTokenResponse(oauthToken, oauthTokenSecret, screenName, null)
        }else{
            AccessTokenResponse(null, null, null, "Unable to parse request token response: $response")
        }
    }
}