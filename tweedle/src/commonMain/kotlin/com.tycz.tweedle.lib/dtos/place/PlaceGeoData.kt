package com.tycz.tweedle.lib.dtos.place

import kotlinx.serialization.Serializable

@Serializable
data class PlaceGeoData(
    val bbox: List<Double>? = null,
    val properties: Properties? = null,
    val type: String? = null
)