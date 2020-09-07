package com.tycz.tweedle.lib.dtos.user

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PublicMetrics(
    @field:Json(name = "followers_count") val followers_count: Int?,
    @field:Json(name = "following_count") val following_count: Int?,
    @field:Json(name = "listed_count") val listed_count: Int?,
    @field:Json(name = "tweet_count") val tweet_count: Int?
)