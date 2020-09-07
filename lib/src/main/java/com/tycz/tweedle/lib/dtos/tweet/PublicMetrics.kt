package com.tycz.tweedle.lib.dtos.tweet

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PublicMetrics(
    @field:Json(name = "like_count") val like_count: Int?,
    @field:Json(name = "quote_count") val quote_count: Int?,
    @field:Json(name = "reply_count") val reply_count: Int?,
    @field:Json(name = "retweet_count") val retweet_count: Int?
)