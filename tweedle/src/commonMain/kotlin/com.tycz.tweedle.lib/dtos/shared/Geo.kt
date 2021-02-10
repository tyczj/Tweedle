package com.tycz.tweedle.lib.dtos.shared

import com.tycz.tweedle.lib.dtos.tweet.Coordinates
import kotlinx.serialization.Serializable

@Serializable
data class Geo(
    val coordinates: Coordinates? = null,
    val place_id: String?
)