package com.tycz.twitter.api.sharedmodule

import com.tycz.tweedle.lib.ExperimentalApi
import com.tycz.tweedle.lib.api.Response
import com.tycz.tweedle.lib.authentication.oauth.OAuth1
import com.tycz.tweedle.lib.authentication.oauth.OAuth2Bearer
import com.tycz.tweedle.lib.tweets.likes.TweetLikes
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertTrue

class LikesTests {

    val apiKey = ""
    val apiSecret = ""
    val oauthKey = ""
    val oauthSecret = ""

    @OptIn(ExperimentalApi::class)
    @Test
    fun getUserLikesTest() = runBlocking {
        val parameters = hashMapOf("user.fields" to "created_at,description,entities,id,location,name,pinned_tweet_id,profile_image_url,protected,public_metrics,url,username,verified,withheld",
            "tweet.fields" to "attachments,author_id,context_annotations,conversation_id,created_at,entities,geo,id,in_reply_to_user_id,lang,non_public_metrics,public_metrics,organic_metrics,promoted_metrics,possibly_sensitive,referenced_tweets,reply_settings,source,text,withheld")
        val oauth = OAuth1(apiKey, apiSecret, oauthKey, oauthSecret)
        val likes = TweetLikes(oauth)
        val response = likes.getUserLikedTweets(0, parameters)
        assertTrue(response is Response.Success)
    }

    @OptIn(ExperimentalApi::class)
    @Test
    fun getLikesForTweet() = runBlocking {
        val parameters = hashMapOf("user.fields" to "created_at,description,entities,id,location,name,pinned_tweet_id,profile_image_url,protected,public_metrics,url,username,verified,withheld",
            "tweet.fields" to "attachments,author_id,context_annotations,conversation_id,created_at,entities,geo,id,in_reply_to_user_id,lang,public_metrics,possibly_sensitive,referenced_tweets,reply_settings,source,text,withheld")
        val oauth = OAuth1(apiKey, apiSecret, oauthKey, oauthSecret)
        val likes = TweetLikes(oauth)
        val response = likes.getLikesForTweet(1499158989388632075, parameters)
        assertTrue(response is Response.Success)
    }

    @Test
    fun likeTweetTest() = runBlocking {
        val oauth = OAuth2Bearer("")
        val likes = TweetLikes(oauth)
        val response = likes.likeTweet(1499158989388632075, 0)
        assertTrue(response is Response.Success)
    }

    @Test
    fun unlikeTweetTest() = runBlocking {
        val oauth = OAuth2Bearer("")
        val likes = TweetLikes(oauth)
        val response = likes.unlikeTweet(1499158989388632075, 0)
        assertTrue(response is Response.Success)
    }
}