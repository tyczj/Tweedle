package com.tycz.tweedle.lib.dtos.media

import com.tycz.tweedle.lib.dtos.shared.Includes
import kotlinx.serialization.Serializable

@Serializable
data class Payload(
    val `data`: List<MediaData>,
    val includes: Includes?
)