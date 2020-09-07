package com.tycz.tweedle.lib.dtos.tweet

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReferencedTweet(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "type") val type: String?
)