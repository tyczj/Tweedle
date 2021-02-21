package com.tycz.tweedle.lib.authentication

import com.tycz.tweedle.lib.generateHmacSha1Signature
import com.tycz.tweedle.lib.urlEncodeString
import io.ktor.http.*
import io.ktor.http.auth.*

class SignatureBuilder {

    private val signatureBuilder = StringBuilder()

    fun createSignature(params: SignatureParams): String{
        val consumerKey = "${HttpAuthHeader.Parameters.OAuthConsumerKey}=\"${params.apiKey}\""
        val callback = "${HttpAuthHeader.Parameters.OAuthCallback}=\"${params.callbackUrl}\""
        val method = "${HttpAuthHeader.Parameters.OAuthSignatureMethod}=\"HMAC-SHA1\""
        val version = "${HttpAuthHeader.Parameters.OAuthVersion}=\"1.0\""
        val timestamp = "${HttpAuthHeader.Parameters.OAuthTimestamp}=\"${params.epochSeconds}\""

        val nonce = "${HttpAuthHeader.Parameters.OAuthNonce}=\"${params.nonce}\""

        val signingKey = "${urlEncodeString(params.apiKey)}&${urlEncodeString(params.apiSecret)}"

        signatureBuilder.append("POST")
        signatureBuilder.append("&")
        signatureBuilder.append(urlEncodeString("https://api.twitter.com/oauth/request_token"))
        signatureBuilder.append("&")
        signatureBuilder.append(urlEncodeString(callback.encodeOAuth()))
        signatureBuilder.append(urlEncodeString("&"))
        signatureBuilder.append(urlEncodeString(consumerKey))
        signatureBuilder.append(urlEncodeString("&"))
        signatureBuilder.append(urlEncodeString(nonce))
        signatureBuilder.append(urlEncodeString("&"))
        signatureBuilder.append(urlEncodeString(method))
        signatureBuilder.append(urlEncodeString("&"))
        signatureBuilder.append(urlEncodeString(timestamp))
        signatureBuilder.append(urlEncodeString("&"))
        signatureBuilder.append(urlEncodeString(version))

        return generateHmacSha1Signature(signingKey, signatureBuilder.toString())
    }
}