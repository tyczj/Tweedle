package com.tycz.tweedle.lib.dtos.place

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.tycz.tweedle.lib.dtos.shared.Geo

@JsonClass(generateAdapter = true)
data class PlaceData(
    @field:Json(name = "geo") val geo: Geo?,
    @field:Json(name = "id") val id: String?,
    @field:Json(name = "text") val text: String?
)