package com.tycz.tweedle.lib.authentication

import com.tycz.tweedle.lib.epochSeconds
import com.tycz.tweedle.lib.generateHmacSha1Signature
import com.tycz.tweedle.lib.getRandomInteger
import com.tycz.tweedle.lib.urlEncodeString
import io.ktor.http.auth.*

internal class SignatureBuilder {

    companion object{
        const val HTTP_POST = "POST"
        const val HTTP_GET = "GET"
        const val HTTP_PUT = "PUT"
        const val HTTP_DELETE = "DELETE"

        const val AUTH_REQUEST_TOKEN_URL = "https://api.twitter.com/oauth/request_token"
    }

    fun createSignature(params: SignatureParams): String{
        val signatureBuilder = StringBuilder()

        val combinedParams:MutableMap<String, String> = mutableMapOf()

        params.urlParams?.let {
            params.urlParams!!.forEach {
                combinedParams[it.key] = it.value
            }
        }

        params.authParams[HttpAuthHeader.Parameters.OAuthConsumerKey] = params.apiKey
        params.authParams[HttpAuthHeader.Parameters.OAuthSignatureMethod] = "HMAC-SHA1"
        params.authParams[HttpAuthHeader.Parameters.OAuthVersion] = "1.0"
        params.authParams[HttpAuthHeader.Parameters.OAuthTimestamp] = params.epochSeconds.toString()

        if(params.oAuthToken != null){
            params.authParams[HttpAuthHeader.Parameters.OAuthToken] = params.oAuthToken
        }

        params.authParams[HttpAuthHeader.Parameters.OAuthNonce] = params.nonce

        val signingKey = params.apiSecret

        signatureBuilder.append(params.httpMethod)
        signatureBuilder.append("&")
        signatureBuilder.append(urlEncodeString(params.url))
        signatureBuilder.append("&")
        if(params.callbackUrl != null && params.callbackUrl.isNotBlank()){
            params.authParams[HttpAuthHeader.Parameters.OAuthCallback] = params.callbackUrl
        }

        params.authParams.forEach {
            combinedParams[it.key] = it.value
        }

        val sortedParams = combinedParams.entries.sortedBy{ it.key }
        sortedParams.forEachIndexed { index, mutableEntry ->
            val param = if(mutableEntry.key == HttpAuthHeader.Parameters.OAuthCallback){
                "${mutableEntry.key}=${mutableEntry.value}"
            }else{
                "${mutableEntry.key}=${urlEncodeString(mutableEntry.value)}"
            }
            signatureBuilder.append(urlEncodeString(param))
            if(index < combinedParams.size-1){
                signatureBuilder.append(urlEncodeString("&"))
            }
        }

        return generateHmacSha1Signature(signingKey, signatureBuilder.toString())
    }

    fun generateNonce(): String{
        val epoch = epochSeconds()
        return (epoch + getRandomInteger()).toString()
    }

    fun generateTimeStamp(): Long{
        return epochSeconds()
    }
}