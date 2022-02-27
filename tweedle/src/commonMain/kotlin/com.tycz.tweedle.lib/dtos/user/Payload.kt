package com.tycz.tweedle.lib.dtos.user

import com.tycz.tweedle.lib.dtos.shared.Includes
import kotlinx.serialization.Serializable

@Serializable
data class Payload(
    val `data`: List<User>,
    val includes: Includes? = null
)