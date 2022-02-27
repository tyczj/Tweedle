package com.tycz.tweedle.lib.dtos.user.follows

import kotlinx.serialization.Serializable

@Serializable
data class FollowersPayload(
    val `data`: List<FollowerData>,
    val meta: Meta
)