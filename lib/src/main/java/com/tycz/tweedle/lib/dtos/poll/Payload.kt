package com.tycz.tweedle.lib.dtos.poll

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.tycz.tweedle.lib.dtos.shared.Includes

@JsonClass(generateAdapter = true)
data class Payload(
    @field:Json(name = "data") val `data`: List<PollData>,
    @field:Json(name = "includes") val includes: Includes?
)