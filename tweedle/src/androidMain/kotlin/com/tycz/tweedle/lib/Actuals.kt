package com.tycz.tweedle.lib

import com.tycz.tweedle.lib.api.TwitterClient
import com.tycz.tweedle.lib.tweets.stream.filter.Filter
import io.ktor.client.engine.android.*
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.Base64.getEncoder
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

actual val engine by lazy { Android.create() }

actual fun epochMillis() = System.currentTimeMillis()

actual fun epochSeconds() = System.currentTimeMillis() / 1000

actual fun urlEncodeString(stringToEncode: String): String{
    return URLEncoder.encode(stringToEncode, StandardCharsets.UTF_8.toString())
}

actual fun getRandomInteger() = Random().nextInt()

actual fun generateHmacSha1Signature(key: String, value: String): String{
    val type = "HmacSHA1"
    val spec = SecretKeySpec(key.toByteArray(), type)
    val mac = Mac.getInstance(type)
    mac.init(spec)
    val bytes = mac.doFinal(value.toByteArray())
    return getEncoder().encodeToString(bytes).replace("\r\n", "")
}