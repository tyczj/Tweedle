package com.tycz.tweedle.lib

import io.ktor.client.engine.ios.*
import kotlinx.cinterop.*
import kotlinx.cinterop.nativeHeap.alloc
import platform.CoreCrypto.*
import platform.Foundation.*
import platform.posix.strlen

actual val engine by lazy { Ios.create() }
actual fun epochMillis() = (NSDate().timeIntervalSince1970 * 1000).toLong()
actual fun epochSeconds() = (NSDate().timeIntervalSince1970).toLong()
actual fun getRandomInteger() = 1
actual fun urlEncodeString(stringToEncode:String): String{
    val s = NSString.create(string = stringToEncode)

    return s.stringByAddingPercentEncodingWithAllowedCharacters(
        NSCharacterSet.URLQueryAllowedCharacterSet()) ?: ""
}

actual fun generateHmacSha1Signature(key: String, value: String): String{
    var digestData: NSData? = null
    val data = value.encodeToByteArray()
    val keyData = key.encodeToByteArray()
    val digestRaw = UByteArray(CC_SHA1_DIGEST_LENGTH)
    val context = nativeHeap.alloc<CCHmacContext>()
    data.usePinned { dataPinned ->
        keyData.usePinned { keyDataPinned ->
            digestRaw.usePinned { digestRawPinned ->
                CCHmacInit(context.ptr, kCCHmacAlgSHA1, keyDataPinned.addressOf(0), keyData.size.convert())
                CCHmacUpdate(context.ptr, dataPinned.addressOf(0), data.size.convert())
                CCHmacFinal(context.ptr, digestRawPinned.addressOf(0))
                digestData = NSData.dataWithBytes(digestRawPinned.addressOf(0), CC_SHA1_DIGEST_LENGTH)
            }
        }
    }
//    val s = ""
//    val t = s.cStringUsingEncoding(NSUTF16StringEncoding)
//    NSString.stringEncodingForData(digestData!!, null, t, null)
    digestData?.let {
//        return NSString.create(it, NSUTF8StringEncoding).toString()
        return it.base64EncodedStringWithOptions(NSDataBase64Encoding64CharacterLineLength)
    }

//    return digestData!!.base64Encoding()
    return ""
//    val nsKey = NSString.create(string = key)
//    val nsValue = NSString.create(string = value)
//    val encodedKey = nsKey.cStringUsingEncoding(NSUTF16StringEncoding)
//    val encodedValue = nsValue.cStringUsingEncoding(NSUTF16StringEncoding)
//
//    val cHMAC = ByteArray(CC_SHA1_DIGEST_LENGTH)
//
//    CCHmac(kCCHmacAlgSHA1, encodedKey, strlen(encodedKey?.toKString()), encodedValue, strlen(encodedValue?.toKString()), cHMAC.asUByteArray().toCValues())
//
//    val data:NSData = NSData.create(bytes = allocArrayOf(cHMAC), length = cHMAC.size.toULong())
//
//    return data.base64EncodedStringWithOptions(NSDataBase64Encoding64CharacterLineLength)
}
actual fun testApi(): String{
    return ""
}