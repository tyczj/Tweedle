package com.tycz.tweedle.lib.dtos.tweet

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Mention(
    @field:Json(name = "end") val end: Int?,
    @field:Json(name = "start") val start: Int?,
    @field:Json(name = "tag") val tag: String?
)