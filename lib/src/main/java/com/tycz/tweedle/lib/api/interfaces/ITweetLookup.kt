package com.tycz.tweedle.lib.api.interfaces

import com.tycz.tweedle.lib.dtos.tweet.MultipleTweetPayload
import com.tycz.tweedle.lib.dtos.tweet.SingleTweetPayload
import okhttp3.Response
import retrofit2.http.*

internal interface ITweetLookup {

    @GET("tweets/{id}")
    suspend fun getSingleTweet(@Header("Authorization") token:String, @Path("id") tweetId:Long): SingleTweetPayload

    @GET("tweets")
    suspend fun getMultipleTweets(@Header("Authorization") token:String, @Query("ids") ids: String): List<MultipleTweetPayload>

    @GET("tweets/search/recent")
    suspend fun getRecentTweets(@Header("Authorization") token:String, @Query("query") query:String, @QueryMap additionalParameters:Map<String, String>):MultipleTweetPayload

    @PUT("tweets/{id}/hidden")
    suspend fun hideTweet(@Header("Authorization") token:String, @Path("id") tweetId:Long, @Body hide:String = "{\\n    \\\"hidden\\\": true\\n}"): Response

    @PUT("tweets/{id}/hidden")
    suspend fun unhideTweet(@Header("Authorization") token:String, @Path("id") tweetId:Long, @Body hide:String = "{\\n    \\\"hidden\\\": false\\n}"): Response
}