package com.tycz.tweedle.lib.dtos.user.follows

import kotlinx.serialization.Serializable

@Serializable
data class FollowingPayload(
    val `data`: Following
)