package com.tycz.tweedle.lib

import io.ktor.client.engine.ios.*
import platform.Foundation.*

actual val engine by lazy { Ios.create() }
actual fun epochMillis() = (NSDate().timeIntervalSince1970 * 1000).toLong()
actual fun epochSeconds() = (NSDate().timeIntervalSince1970).toLong()

actual fun urlEncodeString(stringToEncode:String): String{
    val s = NSString.create(string = stringToEncode)

    return s.stringByAddingPercentEncodingWithAllowedCharacters(
        NSCharacterSet.URLQueryAllowedCharacterSet()) ?: ""
}

actual fun generateHmacSha1Signature(key: String, value: String): String{
    return ""
}