package com.tycz.tweedle.lib.dtos.place

import com.tycz.tweedle.lib.dtos.shared.Geo
import kotlinx.serialization.Serializable

@Serializable
data class PlaceData(
    val geo: Geo?,
    val id: String?,
    val text: String?
)