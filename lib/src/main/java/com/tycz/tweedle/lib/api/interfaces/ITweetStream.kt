package com.tycz.tweedle.lib.api.interfaces

import com.tycz.tweedle.lib.dtos.tweet.rules.*
import okhttp3.ResponseBody
import retrofit2.http.*

interface ITweetStream {

    @POST("tweets/search/stream/rules")
    suspend fun addStreamRules(@Header("Authorization") token:String, @Body rules: Rule): RuleResponse

    @POST("tweets/search/stream/rules")
    suspend fun deleteRuleByRuleValue(@Header("Authorization") token:String, @Body deleteRule: DeleteRule): DeleteRuleResponse

    @GET("tweets/search/stream/rules")
    suspend fun getStreamRules(@Header("Authorization") token:String): StreamRulesResponse

    @Streaming
    @GET("tweets/search/stream")
    suspend fun getFilteredStream(@Header("Authorization") token:String): ResponseBody

    @Streaming
    @GET("tweets/sampled/stream")
    suspend fun getSampledStream(@Header("Authorization") token:String): ResponseBody
}