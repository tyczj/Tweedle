package com.tycz.tweedle.lib.authentication.oauth

import io.ktor.client.request.*

interface IOAuthBuilder {
    fun buildRequest(): HttpRequestBuilder
}