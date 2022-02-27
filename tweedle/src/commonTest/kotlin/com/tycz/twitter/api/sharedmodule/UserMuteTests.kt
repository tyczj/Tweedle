package com.tycz.twitter.api.sharedmodule

import com.tycz.tweedle.lib.ExperimentalApi
import com.tycz.tweedle.lib.api.Response
import com.tycz.tweedle.lib.authentication.oauth.OAuth1
import com.tycz.tweedle.lib.user.UserMuting
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class UserMuteTests {

    val apiKey = ""
    val apiSecret = ""
    val oauthKey = ""
    val oauthSecret = ""

    @OptIn(ExperimentalApi::class)
    lateinit var oauth: OAuth1
    lateinit var userMuting: UserMuting

    @OptIn(ExperimentalApi::class)
    @BeforeTest
    fun before(){
        oauth = OAuth1(apiKey, apiSecret, oauthKey, oauthSecret)
        userMuting = UserMuting(oauth)
    }

    @Test
    fun userMutesTest() = runBlocking {
        val userId = 0L
        val response = userMuting.getMutedUsers(userId)
        assertTrue(response is Response.Success)
    }
}