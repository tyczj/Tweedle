package com.tycz.tweedle.lib.authentication.oauth

import com.tycz.tweedle.lib.ExperimentalApi

@ExperimentalApi
data class AuthenticationOAuth(val apiKey: String, val secret: String): OAuth1(apiKey, secret, null, null)