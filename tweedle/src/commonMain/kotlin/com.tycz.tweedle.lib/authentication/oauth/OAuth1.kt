package com.tycz.tweedle.lib.authentication.oauth

import com.tycz.tweedle.lib.ExperimentalApi
import com.tycz.tweedle.lib.authentication.SignatureBuilder
import com.tycz.tweedle.lib.authentication.SignatureParams
import com.tycz.tweedle.lib.urlEncodeString
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.auth.*

@ExperimentalApi
open class OAuth1(private val key: String, private val secret: String, private val oAuthToken: String?, private val oAuthSecret: String?): IOAuthBuilder {

    private val _signatureBuilder = SignatureBuilder()

    /**
     * Set if the request requires some callback url
     */
    internal var callbackUrl: String? = null
    internal var httpMethod: String? = null
    internal var url: String? = null
    internal var parameters: MutableMap<String, String>? = null

    override fun buildRequest(): HttpRequestBuilder {

        if(httpMethod == null){
            throw IllegalArgumentException("Must set httpMethod for OAuth1")
        }

        if(url == null){
            throw IllegalArgumentException("Must set url for OAuth1")
        }

        val headerBuilder = StringBuilder()

        val epoch = _signatureBuilder.generateTimeStamp()

        val s = _signatureBuilder.generateNonce()

        var encodedCallback = ""
        callbackUrl?.let {
            encodedCallback = urlEncodeString(callbackUrl!!)
        }

        val signingKey = if(oAuthSecret != null){
            "${urlEncodeString(secret)}&${urlEncodeString(oAuthSecret)}"
        }else{
            "${urlEncodeString(secret)}&"
        }

        val authParameters: MutableMap<String, String> = mutableMapOf()

        val authSig = _signatureBuilder.createSignature(
            SignatureParams(httpMethod!!, encodedCallback, key, signingKey, s, epoch, url!!, oAuthToken, parameters, authParameters)
        )

        parameters?.let {
            val paramBuilder = StringBuilder()
            parameters!!.onEachIndexed { index, entry ->
                paramBuilder.append("${entry.key}=${urlEncodeString(entry.value)}")
                if(index < parameters!!.entries.size - 1){
                    paramBuilder.append("&")
                }
            }
        }

        headerBuilder.append("${AuthScheme.OAuth} ")

        var oauthToken = ""
        oAuthToken?.let {
            oauthToken = urlEncodeString(oAuthToken)
        }

        headerBuilder.append("${HttpAuthHeader.Parameters.OAuthConsumerKey}=\"${authParameters[HttpAuthHeader.Parameters.OAuthConsumerKey]!!}\", ")
        headerBuilder.append("${HttpAuthHeader.Parameters.OAuthNonce}=\"${authParameters[HttpAuthHeader.Parameters.OAuthNonce]!!}\", ")
        headerBuilder.append("${HttpAuthHeader.Parameters.OAuthSignature}=\"${urlEncodeString(authSig)}\", ")
        headerBuilder.append("${HttpAuthHeader.Parameters.OAuthSignatureMethod}=\"${authParameters[HttpAuthHeader.Parameters.OAuthSignatureMethod]!!}\", ")

        if(encodedCallback.isNotBlank()){
            headerBuilder.append("${HttpAuthHeader.Parameters.OAuthCallback}=\"${authParameters[HttpAuthHeader.Parameters.OAuthCallback]!!}\", ")
        }

        headerBuilder.append("${HttpAuthHeader.Parameters.OAuthTimestamp}=\"${authParameters[HttpAuthHeader.Parameters.OAuthTimestamp]!!}\", ")

        if(oauthToken.isNotBlank()){
            headerBuilder.append("${HttpAuthHeader.Parameters.OAuthToken}=\"${authParameters[HttpAuthHeader.Parameters.OAuthToken]!!}\", ")
        }

        headerBuilder.append("${HttpAuthHeader.Parameters.OAuthVersion}=\"${authParameters[HttpAuthHeader.Parameters.OAuthVersion]!!}\"")

        val builder = HttpRequestBuilder()
        builder.header(HttpHeaders.Authorization, headerBuilder.toString())

        return builder
    }
}