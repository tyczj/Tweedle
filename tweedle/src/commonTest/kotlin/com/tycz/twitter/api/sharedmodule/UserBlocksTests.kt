package com.tycz.twitter.api.sharedmodule

import com.tycz.tweedle.lib.ExperimentalApi
import com.tycz.tweedle.lib.api.Response
import com.tycz.tweedle.lib.authentication.oauth.OAuth1
import com.tycz.tweedle.lib.authentication.oauth.OAuth2Bearer
import com.tycz.tweedle.lib.user.UserBlocks
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class UserBlocksTests {

    val apiKey = ""
    val apiSecret = ""
    val oauthKey = ""
    val oauthSecret = ""

    @OptIn(ExperimentalApi::class)
    lateinit var oauth: OAuth1
    lateinit var userBlocks: UserBlocks

    @OptIn(ExperimentalApi::class)
    @BeforeTest
    fun before(){
        oauth = OAuth1(apiKey, apiSecret, oauthKey, oauthSecret)
        userBlocks = UserBlocks(oauth)
    }

    @Test
    fun getBlockedUsersTest() = runBlocking {
        val userId = 0L
        val response = userBlocks.getBlockedUsers(userId)
        assertTrue(response is Response.Success)
    }

    @Test
    fun blockUserTest() = runBlocking {
        val oauth = OAuth2Bearer("")
        userBlocks = UserBlocks(oauth)
        userBlocks.blockUser(0,0)
    }

    @Test
    fun unblockUserTest() = runBlocking {
        val oauth = OAuth2Bearer("")
        userBlocks = UserBlocks(oauth)
        userBlocks.unblockUser(0, 0)
    }
}