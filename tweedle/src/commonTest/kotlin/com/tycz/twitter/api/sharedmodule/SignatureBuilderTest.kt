package com.tycz.twitter.api.sharedmodule

import com.tycz.tweedle.lib.authentication.SignatureBuilder
import kotlin.test.Test
import kotlin.test.assertNotNull

class SignatureBuilderTest {

    @Test
    fun testSignature(){
        val sBuilder = SignatureBuilder()

        val sig = sBuilder.createSignature("https://inlighten.net", "26BVvThkUuerm0IauIyK6kiUA","YBPScGOH8mqdKqW2GYIiRV7M3ocxvWMk3ntEEVMGC45Q870NxH")
        assertNotNull(sig)
    }
}