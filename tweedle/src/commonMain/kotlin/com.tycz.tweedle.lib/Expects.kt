package com.tycz.tweedle.lib

import io.ktor.client.engine.*

expect val engine: HttpClientEngine
expect fun epochMillis(): Long
expect fun epochSeconds(): Long
expect fun urlEncodeString(stringToEncode:String): String
expect fun generateHmacSha1Signature(key: String, value: String): String
expect fun getRandomInteger(): Int