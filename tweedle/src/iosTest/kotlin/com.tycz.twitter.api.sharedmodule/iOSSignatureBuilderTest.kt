package com.tycz.twitter.api.sharedmodule

import com.tycz.tweedle.lib.generateHmacSha1Signature
import kotlin.test.Test
import kotlin.test.assertNotNull

class iOSSignatureBuilderTest {

    @Test
    fun testSha1Signature(){
//        val sig = "POST&https%3A%2F%2Fapi.twitter.com%2Foauth%2Frequest_token&oauth_callback%253D%2522https%253A%252F%252Finlighten.net%2522%26oauth_consumer_key%3D%2226BVvThkUuerm0IauIyK6kiUA%22%26oauth_nonce%3D%223EQyItekWIuw6cQyPNDwsVmomn9Cakuc%22%26oauth_signature_method%3D%22HMAC-SHA1%22%26oauth_timestamp%3D%221613960716%22%26oauth_version%3D%221.0%22"
//        val key = "26BVvThkUuerm0IauIyK6kiUA&YBPScGOH8mqdKqW2GYIiRV7M3ocxvWMk3ntEEVMGC45Q870NxH"
//
//        val signature = generateHmacSha1Signature(key, sig)
//        println(signature)
//        assertNotNull(signature)
    }
}