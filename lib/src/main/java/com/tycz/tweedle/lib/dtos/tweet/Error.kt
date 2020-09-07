package com.tycz.tweedle.lib.dtos.tweet

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Error(
    @field:Json(name = "details") val details: List<String>?,
    @field:Json(name = "title") val title: String?,
    @field:Json(name = "type") val type: String?,
    @field:Json(name = "value") val value: String?
)