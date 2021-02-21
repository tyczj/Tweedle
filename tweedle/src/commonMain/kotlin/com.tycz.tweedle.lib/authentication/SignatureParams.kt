package com.tycz.tweedle.lib.authentication

data class SignatureParams(
    val callbackUrl: String,
    val apiKey: String,
    val apiSecret: String,
    val nonce: String,
    val epochSeconds: Long
    )