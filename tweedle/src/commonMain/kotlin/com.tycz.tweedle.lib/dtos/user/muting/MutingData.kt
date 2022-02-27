package com.tycz.tweedle.lib.dtos.user.muting

import kotlinx.serialization.Serializable

@Serializable
data class MutingData(
    val description: String,
    val id: String,
    val name: String,
    val profile_image_url: String,
    val username: String
)
