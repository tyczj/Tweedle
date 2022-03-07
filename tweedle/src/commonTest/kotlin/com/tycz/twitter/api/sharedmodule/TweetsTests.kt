package com.tycz.twitter.api.sharedmodule

import com.tycz.tweedle.lib.ExperimentalApi
import com.tycz.tweedle.lib.api.Response
import com.tycz.tweedle.lib.authentication.Authentication2
import com.tycz.tweedle.lib.authentication.oauth.OAuth1
import com.tycz.tweedle.lib.authentication.oauth.OAuth2Bearer
import com.tycz.tweedle.lib.dtos.tweet.MultipleTweetPayload
import com.tycz.tweedle.lib.dtos.tweet.SingleTweetPayload
import com.tycz.tweedle.lib.tweets.lookup.TweetsLookup
import com.tycz.tweedle.lib.tweets.stream.filter.Filter
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class TweetsTests {

    val apiKey = ""
    val apiSecret = ""
    val oauthKey = ""
    val oauthSecret = ""

    @OptIn(ExperimentalApi::class)
    lateinit var oauth: OAuth1
    lateinit var tweetsLookup: TweetsLookup

    @OptIn(ExperimentalApi::class)
    @BeforeTest
    fun before(){
        oauth = OAuth1(apiKey, apiSecret, oauthKey, oauthSecret)
        tweetsLookup = TweetsLookup(oauth)
    }

    @ExperimentalApi
    @Test
    fun testSingleTweet() = runBlocking{
        val response: Response<SingleTweetPayload?> = tweetsLookup.getTweet(1299418846990921728)
        assertTrue(response is Response.Success)
    }

    @ExperimentalApi
    @Test
    fun testMultipleTweets() = runBlocking {
        val response: Response<MultipleTweetPayload?> = tweetsLookup.getMultipleTweets(mutableListOf(1299418846990921728, 1387852271938150408))
        assertTrue(response is Response.Success)
    }

    @ExperimentalApi
    @Test
    fun testRecentTweets() = runBlocking {

        val map = HashMap<String, String>()
        map["tweet.fields"] = "lang"
        map["expansions"] = "attachments.media_keys"
        map["media.fields"] = "preview_image_url,url"

        val filter: Filter = Filter.Builder()
            .addOperator("from:TwitterDev")
            .build()

        val response: Response<MultipleTweetPayload?> = tweetsLookup.getRecentTweets(filter.filter,map)
        assertTrue(response is Response.Success)
    }

    @ExperimentalApi
    @Test
    fun testHideTweetOAuth1() = runBlocking{
        val oauth = OAuth2Bearer("")
        val tweetLookup = TweetsLookup(oauth)
        val response = tweetLookup.hideTweet(1116135074787803136)
        assertTrue(response is Response.Success)
    }

    @ExperimentalApi
    @Test
    fun testUnHideTweetOAuth1() = runBlocking{
        val oauth = OAuth2Bearer("")
        val tweetLookup = TweetsLookup(oauth)
        val response = tweetLookup.unhideTweet(1116135074787803136)
        assertTrue(response is Response.Success)
    }

    @Test
    fun testRefreshToken() = runBlocking {
        val auth = Authentication2("", "")
        val response = auth.refreshToken("")
        assertTrue(response is Response.Success)
    }
}