package com.tycz.tweedle.lib.dtos.tweet

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Annotation(
    @field:Json(name = "end") val end: Int?,
    @field:Json(name = "normalized_text") val normalized_text: String?,
    @field:Json(name = "probability") val probability: Double?,
    @field:Json(name = "start") val start: Int?,
    @field:Json(name = "type") val type: String?
)