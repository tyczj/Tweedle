package com.tycz.tweedle.lib.dtos.tweet

import kotlinx.serialization.Serializable

@Serializable
data class Coordinates(
    val coordinates: List<Double>? = null,
    val type: String? = null
)