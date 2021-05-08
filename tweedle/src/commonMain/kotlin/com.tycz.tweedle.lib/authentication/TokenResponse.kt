package com.tycz.tweedle.lib.authentication

data class TokenResponse(
    val oauthToken: String?,
    val oauthTokenSecret: String?,
    val oauthCallbackConfirmed: Boolean?,
    val error: String?
)
