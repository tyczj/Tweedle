package com.tycz.tweedle.lib.dtos.tweet

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Image(
    @field:Json(name = "height") val height: Int?,
    @field:Json(name = "url") val url: String?,
    @field:Json(name = "width") val width: Int?
)