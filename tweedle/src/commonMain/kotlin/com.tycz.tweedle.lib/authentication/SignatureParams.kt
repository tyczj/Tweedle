package com.tycz.tweedle.lib.authentication

internal data class SignatureParams(
    val httpMethod: String,
    val callbackUrl: String?,
    val apiKey: String,
    val apiSecret: String,
    val nonce: String,
    val epochSeconds: Long,
    val url: String,
    val oAuthToken: String?,
    var urlParams: MutableMap<String, String>?,
    var authParams: MutableMap<String, String>
    )