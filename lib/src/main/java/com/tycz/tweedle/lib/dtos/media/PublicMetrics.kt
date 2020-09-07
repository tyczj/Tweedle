package com.tycz.tweedle.lib.dtos.media

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PublicMetrics(
    @field:Json(name = "view_count") val view_count: Int?
)