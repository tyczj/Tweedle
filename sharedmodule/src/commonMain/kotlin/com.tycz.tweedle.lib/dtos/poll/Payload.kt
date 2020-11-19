package com.tycz.tweedle.lib.dtos.poll

import com.tycz.tweedle.lib.dtos.shared.Includes
import kotlinx.serialization.Serializable

@Serializable
data class Payload(
    val `data`: List<PollData>,
    val includes: Includes?
)