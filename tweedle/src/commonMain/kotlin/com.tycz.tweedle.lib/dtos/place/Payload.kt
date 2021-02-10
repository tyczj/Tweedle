package com.tycz.tweedle.lib.dtos.place

import com.tycz.tweedle.lib.dtos.shared.Includes
import kotlinx.serialization.Serializable

@Serializable
data class Payload(
    val `data`: List<PlaceData>,
    val includes: Includes?
)