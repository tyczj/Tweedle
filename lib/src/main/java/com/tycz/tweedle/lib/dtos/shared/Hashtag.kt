package com.tycz.tweedle.lib.dtos.shared

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Hashtag(
    @field:Json(name = "end") val end: Int?,
    @field:Json(name = "start") val start: Int?,
    @field:Json(name = "tag") val tag: String?
)