package com.tycz.tweedle.lib.dtos.user.muting

import kotlinx.serialization.Serializable

@Serializable
data class MutingPayload(
    val `data`: List<MutingData>
)
