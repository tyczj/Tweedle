package com.tycz.tweedle.lib.dtos.user.likes

import kotlinx.serialization.Serializable

@Serializable
data class LikeData(
    val liked: Boolean? = null
)