package com.tycz.tweedle.lib.dtos.media

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NonPublicMetrics(
    @field:Json(name = "playback_0_count") val playback_0_count: Int?,
    @field:Json(name = "playback_100_count") val playback_100_count: Int?,
    @field:Json(name = "playback_25_count") val playback_25_count: Int?,
    @field:Json(name = "playback_50_count") val playback_50_count: Int?,
    @field:Json(name = "playback_75_count") val playback_75_count: Int?
)