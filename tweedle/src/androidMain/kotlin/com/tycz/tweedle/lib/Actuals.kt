package com.tycz.tweedle.lib

import io.ktor.client.engine.android.*
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec


actual val engine by lazy { Android.create() }

actual fun epochMillis() = System.currentTimeMillis()

actual fun epochSeconds() = System.currentTimeMillis() / 1000

actual fun urlEncodeString(stringToEncode: String): String{
    return URLEncoder.encode(stringToEncode, StandardCharsets.UTF_8.toString())
}

actual fun generateHmacSha1Signature(key: String, value: String): String{
    val type = "HmacSHA1"
    val spec = SecretKeySpec(key.toByteArray(), type)
    val mac = Mac.getInstance(type)
    mac.init(spec)
    val bytes = mac.doFinal(value.toByteArray())
    return bytesToHex(bytes)
}

private val hexArray = "0123456789abcdef".toCharArray()

private fun bytesToHex(bytes: ByteArray): String{
    val result = CharArray(bytes.size * 2)
    var resultIndex = 0
    val digits = hexArray

    for (element in bytes) {
        val b = element.toInt() and 0xff
        result[resultIndex++] = digits[b shr 4]
        result[resultIndex++] = digits[b and 0x0f]
    }

    return result.concatToString()
}