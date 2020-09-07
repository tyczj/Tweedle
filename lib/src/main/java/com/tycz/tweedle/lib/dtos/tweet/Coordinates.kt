package com.tycz.tweedle.lib.dtos.tweet

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Coordinates(
    @field:Json(name = "coordinates") val coordinates: List<Double>?,
    @field:Json(name = "type") val type: String?
)