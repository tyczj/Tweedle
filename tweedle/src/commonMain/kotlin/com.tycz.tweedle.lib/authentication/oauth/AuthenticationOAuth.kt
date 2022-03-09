package com.tycz.tweedle.lib.authentication.oauth

data class AuthenticationOAuth(val apiKey: String, val secret: String): OAuth1(apiKey, secret, null, null)