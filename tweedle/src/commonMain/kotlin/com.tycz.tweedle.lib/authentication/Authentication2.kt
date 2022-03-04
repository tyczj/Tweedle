package com.tycz.tweedle.lib.authentication

import com.tycz.tweedle.lib.api.Response
import com.tycz.tweedle.lib.api.TwitterClient
import com.tycz.tweedle.lib.authentication.oauth.OAuthScope
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class Authentication2 (private val code: String, private val clientId: String) {

    companion object{
        /**
         * Generate authentication url
         *
         * @param clientId Client ID found in the twitter developer portal
         * @param scopes All the different access scopes to request
         * @param callbackUrl
         * @param state A random string you provide to verify against CSRF attacks.  The length of this string can be up to 500 characters.
         * @param challenge This is a cryptographically random string using the characters A-Z, a-z, 0-9, and the punctuation characters -._~ (hyphen, period, underscore, and tilde), between 43 and 128 characters long.
         */
        fun generateAuthenticationUrl(clientId: String, scopes: List<OAuthScope>, callbackUrl: String, state: String, challenge: String): String{
            val scope = scopes.joinToString("%20") { it.value }
            return "https://twitter.com/i/oauth2/authorize?response_type=code&client_id=$clientId&redirect_uri=$callbackUrl&state=$state&code_challenge=$challenge&code_challenge_method=plain&scope=$scope"
        }
    }

    private val _client = TwitterClient.instance

    /**
     * Gets a usable access token.
     * Tokens are only good for 2 hours then they need to be refreshed or you need to login again
     *
     * @param redirectUrl Your callback url
     * @param challenge Challenge you sent in in the Authentication URL
     * @return
     */
    suspend fun getAccessToken(redirectUrl: String, challenge: String): Response<OAuth2PKCEResponse?> {

        val url = "${TwitterClient.BASE_URL}${TwitterClient.OAUTH2_ENDPOINT}/${TwitterClient.OAUTH2_TOKEN_ENDPOINT}?code=$code&grant_type=authorization_code&client_id=$clientId&redirect_uri=$redirectUrl&code_verifier=$challenge"

        return try{
            val builder = HttpRequestBuilder()
            builder.url(URLBuilder(url).build())
            val response = _client.post<OAuth2PKCEResponse>(builder)
            Response.Success(response)
        }catch (e: Exception){
            Response.Error(e)
        }
    }

    /**
     * Refreshes a bearer token, this can only be used if you have requested a token with the scope of offline.access
     *
     * @param refreshToken Refresh token received from the getAccessToken call
     * @return A new bearer token
     */
    suspend fun refreshToken(refreshToken: String): Response<OAuth2PKCEResponse?>{
        val url = "${TwitterClient.BASE_URL}${TwitterClient.OAUTH2_ENDPOINT}/${TwitterClient.OAUTH2_TOKEN_ENDPOINT}?refresh_token=$refreshToken&client_id=$clientId&grant_type=refresh_token"

        return try{
            val builder = HttpRequestBuilder()
            builder.url(URLBuilder(url).build())
            val response = _client.post<OAuth2PKCEResponse>(builder)
            Response.Success(response)
        }catch (e: Exception){
            Response.Error(e)
        }
    }

    /**
     * Revokes an active bearer token for the user
     *
     * @param token The token to revoke
     * @return
     */
    suspend fun revokeToken(token: String): Response<HttpResponse?>{
        val url = "${TwitterClient.BASE_URL}${TwitterClient.OAUTH2_ENDPOINT}/${TwitterClient.OAUTH2_REVOKE_ENDPOINT}?token=$token&client_id=$clientId"

        return try{
            val builder = HttpRequestBuilder()
            builder.url(URLBuilder(url).build())
            val response = _client.post<HttpResponse>(builder)
            Response.Success(response)
        }catch (e: Exception){
            Response.Error(e)
        }
    }
}