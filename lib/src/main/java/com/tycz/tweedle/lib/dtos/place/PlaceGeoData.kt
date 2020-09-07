package com.tycz.tweedle.lib.dtos.place

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlaceGeoData(
    @field:Json(name = "bbox") val bbox: List<Double>?,
    @field:Json(name = "properties") val properties: Properties?,
    @field:Json(name = "type") val type: String?
)