package com.tycz.tweedle.lib.authentication

data class AccessTokenResponse(
    val oauthToken: String?,
    val oauthTokenSecret:String?,
    val screenName:String?,
    val error: String?
)