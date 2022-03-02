package com.tycz.tweedle.lib.authentication.oauth

import io.ktor.client.request.*
import io.ktor.http.*

class OAuth2Bearer(private val token: String): IOAuthBuilder{

    override fun buildRequest(): HttpRequestBuilder {
        val builder = HttpRequestBuilder()
        builder.header(HttpHeaders.Authorization, "Bearer $token")
        return builder
    }

}