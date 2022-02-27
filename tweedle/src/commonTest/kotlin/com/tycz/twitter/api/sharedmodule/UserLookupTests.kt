package com.tycz.twitter.api.sharedmodule

import com.tycz.tweedle.lib.ExperimentalApi
import com.tycz.tweedle.lib.api.Response
import com.tycz.tweedle.lib.authentication.oauth.OAuth1
import com.tycz.tweedle.lib.user.UserLookup
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.*

class UserLookupTests {

    val apiKey = ""
    val apiSecret = ""
    val oauthKey = ""
    val oauthSecret = ""

    @OptIn(ExperimentalApi::class)
    lateinit var oauth: OAuth1
    lateinit var userLookup: UserLookup

    @OptIn(ExperimentalApi::class)
    @BeforeTest
    fun before(){
        oauth = OAuth1(apiKey, apiSecret, oauthKey, oauthSecret)
        userLookup = UserLookup(oauth)
    }

    @Test
    fun getUserByIdTest() = runBlocking{
        val userId = 0L
        val response = userLookup.getUserById(userId)
        assertTrue(response is Response.Success)
    }

    @Test
    fun getUserByIdWithParamsTest() = runBlocking {
        val parameters = hashMapOf("user.fields" to "description,profile_image_url,id,created_at,location,pinned_tweet_id,protected,url,verified")
        val userId = 0L
        val response = userLookup.getUserById(userId, parameters)
        assertTrue(response is Response.Success)
    }

    @Test
    fun getUsersByIdsTest() = runBlocking {
        val userId = 0L
        val response = userLookup.getUsersByIds(listOf(userId,2244994945))
        assertTrue(response is Response.Success)
    }

    @Test
    fun getUsersByIdsWithParamsTest() = runBlocking {
        val userId = 0L
        val parameters = hashMapOf("user.fields" to "description,profile_image_url,id,created_at,location,pinned_tweet_id,protected,url,verified")
        val response = userLookup.getUsersByIds(listOf(userId,2244994945), parameters)
        assertTrue(response is Response.Success)
    }

    @Test
    fun getUserByUsername() = runBlocking {
        val response = userLookup.getUserByUsername("TwitterDev")
        assertTrue(response is Response.Success)
    }

    @Test
    fun getUserByUsernameWithParams() = runBlocking {
        val parameters = hashMapOf("user.fields" to "description,profile_image_url,id,created_at,location,pinned_tweet_id,protected,url,verified")
        val response = userLookup.getUserByUsername("TwitterDev", parameters)
        assertTrue(response is Response.Success)
    }

    @Test
    fun getUsersByUsername() = runBlocking {
        val response = userLookup.getUsersByUsernames(listOf("TwitterDev","tyczj"))
        assertTrue(response is Response.Success)
    }

    @Test
    fun getUsersByUsernameWithParams() = runBlocking {
        val parameters = hashMapOf("user.fields" to "description,profile_image_url,id,created_at,location,pinned_tweet_id,protected,url,verified")
        val response = userLookup.getUsersByUsernames(listOf("TwitterDev","tyczj"),parameters)
        assertTrue(response is Response.Success)
    }

}