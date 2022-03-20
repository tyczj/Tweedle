package com.tycz.twitter.api.sharedmodule

import com.tycz.tweedle.lib.ExperimentalApi
import com.tycz.tweedle.lib.api.Response
import com.tycz.tweedle.lib.authentication.oauth.OAuth1
import com.tycz.tweedle.lib.authentication.oauth.OAuth2Bearer
import com.tycz.tweedle.lib.user.UserEndpoints
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.*

class UserEndpointsTests {

    val apiKey = ""
    val apiSecret = ""
    val oauthKey = ""
    val oauthSecret = ""

    lateinit var oauth: OAuth1
    lateinit var userLookup: UserEndpoints

    @BeforeTest
    fun before(){
        oauth = OAuth1(apiKey, apiSecret, oauthKey, oauthSecret)
        userLookup = UserEndpoints(oauth)
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

    @Test
    fun getUserTimeline() = runBlocking {
        val parameters = hashMapOf("user.fields" to "description,profile_image_url,id,created_at,location,pinned_tweet_id,protected,url,verified",
        "expansions" to "attachments.poll_ids,attachments.media_keys,author_id,entities.mentions.username,geo.place_id,in_reply_to_user_id,referenced_tweets.id,referenced_tweets.id.author_id",
        "media.fields" to "duration_ms,height,media_key,preview_image_url,type,url,width,public_metrics,non_public_metrics,organic_metrics,promoted_metrics,alt_text",
        "place.fields" to "contained_within,country,country_code,full_name,geo,id,name,place_type",
        "poll.fields" to "duration_minutes,end_datetime,id,options,voting_status",
        "tweet.fields" to "attachments,author_id,context_annotations,conversation_id,created_at,entities,geo,id,in_reply_to_user_id,lang,possibly_sensitive,referenced_tweets,reply_settings,source,text,withheld")
        val response = userLookup.getUserTimeline(0, parameters)
        assertTrue(response is Response.Success)
    }

    @Test
    fun getUserMentions() = runBlocking {
        val parameters = hashMapOf("user.fields" to "description,profile_image_url,id,created_at,location,pinned_tweet_id,protected,url,verified",
            "expansions" to "attachments.poll_ids,attachments.media_keys,author_id,entities.mentions.username,geo.place_id,in_reply_to_user_id,referenced_tweets.id,referenced_tweets.id.author_id",
            "media.fields" to "duration_ms,height,media_key,preview_image_url,type,url,width,public_metrics,non_public_metrics,organic_metrics,promoted_metrics,alt_text",
            "place.fields" to "contained_within,country,country_code,full_name,geo,id,name,place_type",
            "poll.fields" to "duration_minutes,end_datetime,id,options,voting_status",
            "tweet.fields" to "attachments,author_id,context_annotations,conversation_id,created_at,entities,geo,id,in_reply_to_user_id,lang,possibly_sensitive,referenced_tweets,reply_settings,source,text,withheld")
        val response = userLookup.getUserMentions(0, parameters)
        assertTrue(response is Response.Success)
    }

    @Test
    fun getBlockedUsersTest() = runBlocking {
        val userId = 0L
        val response = userLookup.getBlockedUsers(userId)
        assertTrue(response is Response.Success)
    }

    @Test
    fun blockUserTest() = runBlocking {
        val oauth = OAuth2Bearer("")
        userLookup = UserEndpoints(oauth)
        val response = userLookup.blockUser(146633079,2244994945)
        assertTrue(response is Response.Success)
    }

    @Test
    fun unblockUserTest() = runBlocking {
        val oauth = OAuth2Bearer("")
        userLookup = UserEndpoints(oauth)
        val response = userLookup.unblockUser(146633079, 2244994945)
        assertTrue(response is Response.Success)
    }

    @Test
    fun getFollowersTest() = runBlocking {
        val response = userLookup.getFollowers(2244994945)
        assertTrue(response is Response.Success)
    }

    @Test
    fun getFollowingTest() = runBlocking {
        val response = userLookup.getFollowing(2244994945)
        assertTrue(response is Response.Success)
    }

    @Test
    fun followTest() = runBlocking {
        val oauth = OAuth2Bearer("")
        userLookup = UserEndpoints(oauth)
        val userId = 0
        val userToFollowId = 2244994945
        val response = userLookup.followUser(userId.toLong(), userToFollowId)
        assertTrue(response is Response.Success)
    }

    @Test
    fun unfollowTest() = runBlocking {
        val oauth = OAuth2Bearer("")
        userLookup = UserEndpoints(oauth)
        val userId = 0
        val userToFollowId = 2244994945
        val response = userLookup.unfollowUser(userId.toLong(), userToFollowId)
        assertTrue(response is Response.Success)
    }

    @Test
    fun userMutesTest() = runBlocking {
        val userId = 0
        val response = userLookup.getMutedUsers(userId.toLong())
        assertTrue(response is Response.Success)
    }

    @Test
    fun muteUser() = runBlocking {
        val oauth = OAuth2Bearer("")
        userLookup = UserEndpoints(oauth)
        val userId = 146633079
        val userToMute = 2244994945
        val response = userLookup.muteUser(userId.toLong(), userToMute)
        assertTrue(response is Response.Success)
    }

    @Test
    fun unmuteUser() = runBlocking {
        val oauth = OAuth2Bearer("")
        userLookup = UserEndpoints(oauth)
        val userId = 146633079
        val userToUnMute = 2244994945
        val response = userLookup.unmuteUser(userId.toLong(), userToUnMute)
        assertTrue(response is Response.Success)
    }

}