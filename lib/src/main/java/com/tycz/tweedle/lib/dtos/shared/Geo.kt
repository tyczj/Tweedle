package com.tycz.tweedle.lib.dtos.shared

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.tycz.tweedle.lib.dtos.tweet.Coordinates

@JsonClass(generateAdapter = true)
data class Geo(
    @field:Json(name = "coordinates") val coordinates: Coordinates?,
    @field:Json(name = "place_id") val place_id: String?
)