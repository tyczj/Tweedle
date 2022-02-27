package com.tycz.twitter.api.sharedmodule

import com.tycz.tweedle.lib.ExperimentalApi
import com.tycz.tweedle.lib.api.Response
import com.tycz.tweedle.lib.authentication.oauth.OAuth1
import com.tycz.tweedle.lib.user.UserFollowers
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class UserFollowersTests {

    val apiKey = ""
    val apiSecret = ""
    val oauthKey = ""
    val oauthSecret = ""

    @OptIn(ExperimentalApi::class)
    lateinit var oauth: OAuth1
    lateinit var userFollowers: UserFollowers

    @OptIn(ExperimentalApi::class)
    @BeforeTest
    fun before(){
        oauth = OAuth1(apiKey, apiSecret, oauthKey, oauthSecret)
        userFollowers = UserFollowers(oauth)
    }

    @Test
    fun getFollowersTest() = runBlocking {
        val response = userFollowers.getFollowers(2244994945)
        assertTrue(response is Response.Success)
    }

    @Test
    fun getFollowingTest() = runBlocking {
        val response = userFollowers.getFollowing(2244994945)
        assertTrue(response is Response.Success)
    }

    @Test
    fun followTest() = runBlocking {
        val userId = 0L
        val userToFollowId = 2244994945
        val response = userFollowers.followUser(userId.toLong(), userToFollowId)
        assertTrue(response is Response.Success)
    }

    @Test
    fun unfollowTest() = runBlocking {
        val userId = 0L
        val userToFollowId = 2244994945
        val response = userFollowers.unfollowUser(userId.toLong(), userToFollowId)
        assertTrue(response is Response.Success)
    }
}