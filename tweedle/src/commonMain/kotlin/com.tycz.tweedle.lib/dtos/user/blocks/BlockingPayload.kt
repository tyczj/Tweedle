package com.tycz.tweedle.lib.dtos.user.blocks

import kotlinx.serialization.Serializable

@Serializable
data class BlockingPayload(
    val `data`: Blocking
)
