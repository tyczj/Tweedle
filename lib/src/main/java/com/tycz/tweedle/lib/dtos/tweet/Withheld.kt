package com.tycz.tweedle.lib.dtos.tweet

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Withheld(
    @field:Json(name = "copyright") val copyright: Boolean?,
    @field:Json(name = "country_codes") val country_codes: List<String>?,
    @field:Json(name = "scope") val scope: String?
)