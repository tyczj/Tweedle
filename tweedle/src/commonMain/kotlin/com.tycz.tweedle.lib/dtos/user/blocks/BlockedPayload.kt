package com.tycz.tweedle.lib.dtos.user.blocks

import kotlinx.serialization.Serializable

@Serializable
data class BlockedPayload(
    val `data`: List<BlockedData>
)
