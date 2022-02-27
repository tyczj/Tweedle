package com.tycz.tweedle.lib.dtos.user.follows

import kotlinx.serialization.Serializable

@Serializable
data class Following(
    val following: Boolean,
    val pending_follow: Boolean? = null
)