package com.tycz.tweedle.lib.dtos.place

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.tycz.tweedle.lib.dtos.shared.Includes

@JsonClass(generateAdapter = true)
data class Payload(
    @field:Json(name = "data") val `data`: List<PlaceData>,
    @field:Json(name = "includes") val includes: Includes?
)