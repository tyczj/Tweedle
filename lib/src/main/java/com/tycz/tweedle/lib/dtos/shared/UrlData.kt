package com.tycz.tweedle.lib.dtos.shared

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.tycz.tweedle.lib.dtos.tweet.Image

@JsonClass(generateAdapter = true)
data class UrlData(
    @field:Json(name = "description") val description: String?,
    @field:Json(name = "display_url") val display_url: String?,
    @field:Json(name = "end") val end: Int?,
    @field:Json(name = "expanded_url") val expanded_url: String?,
    @field:Json(name = "images") val images: List<Image>?,
    @field:Json(name = "start") val start: Int?,
    @field:Json(name = "status") val status: Int?,
    @field:Json(name = "title") val title: String?,
    @field:Json(name = "unwound_url") val unwound_url: String?,
    @field:Json(name = "url") val url: String?
)