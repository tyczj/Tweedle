package com.tycz.tweedle.lib.dtos.user.follows

import kotlinx.serialization.Serializable

@Serializable
data class FollowerData(
    val description: String,
    val id: String,
    val name: String,
    val profile_image_url: String,
    val username: String
)