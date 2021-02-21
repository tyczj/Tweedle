package com.tycz.twitter.api.sharedmodule

import com.tycz.tweedle.lib.authentication.SignatureBuilder
import com.tycz.tweedle.lib.authentication.SignatureParams
import com.tycz.tweedle.lib.epochSeconds
import kotlin.test.Test
import kotlin.test.assertNotNull

class SignatureBuilderTest {

    @Test
    fun testSignature(){
        val sBuilder = SignatureBuilder()

        val allowedChars = ('A'..'Z') + ('a'..'z') + (0..9)
        val s = (1..32).map { allowedChars.random() }
            .joinToString("")

        val sig = sBuilder.createSignature(SignatureParams("", "", "", s, epochSeconds()))
        assertNotNull(sig)
    }
}