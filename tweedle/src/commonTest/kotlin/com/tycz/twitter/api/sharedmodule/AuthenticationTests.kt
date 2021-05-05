package com.tycz.twitter.api.sharedmodule

import com.tycz.tweedle.lib.ExperimentalApi
import com.tycz.tweedle.lib.api.Response
import com.tycz.tweedle.lib.authentication.Authentication
import com.tycz.tweedle.lib.authentication.oauth.OAuth1
import com.tycz.tweedle.lib.dtos.tweet.MultipleTweetPayload
import com.tycz.tweedle.lib.dtos.tweet.SingleTweetPayload
import com.tycz.tweedle.lib.testApi
import com.tycz.tweedle.lib.tweets.lookup.TweetsLookup
import com.tycz.tweedle.lib.tweets.stream.filter.Filter
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class AuthenticationTests {

    val apiKey = ""
    val apiSecret = ""
    val oauthKey = ""
    val oauthSecret = ""

    @BeforeTest


    @ExperimentalApi
    @Test
    fun testSingleTweetOAuth1() = runBlocking{
        val oauth = OAuth1(apiKey, apiSecret, oauthKey, oauthSecret)

        val tweetLookup = TweetsLookup(oauth)
        val response: Response<SingleTweetPayload?> = tweetLookup.getTweet(1299418846990921728)
        assertTrue(response is Response.Success)
    }

    @ExperimentalApi
    @Test
    fun testMultipleTweetsOAuth1() = runBlocking {
        val oauth = OAuth1(apiKey, apiSecret, oauthKey, oauthSecret)

        val tweetLookup = TweetsLookup(oauth)
        val response: Response<MultipleTweetPayload?> = tweetLookup.getMultipleTweets(mutableListOf(1299418846990921728, 1387852271938150408))
        assertTrue(response is Response.Success)
    }

    @ExperimentalApi
    @Test
    fun testRecentTweetsOAuth1() = runBlocking {
        val oauth = OAuth1(apiKey, apiSecret, oauthKey, oauthSecret)

        val map = HashMap<String, String>()
        map["tweet.fields"] = "lang"
        map["expansions"] = "attachments.media_keys"
        map["media.fields"] = "preview_image_url,url"

        val filter: Filter = Filter.Builder()
            .addOperator("from:TwitterDev")
            .build()

        val tweetLookup = TweetsLookup(oauth)
        val response:Response<MultipleTweetPayload?> = tweetLookup.getRecentTweets(filter.filter,map)
        assertTrue(response is Response.Success)
    }
}