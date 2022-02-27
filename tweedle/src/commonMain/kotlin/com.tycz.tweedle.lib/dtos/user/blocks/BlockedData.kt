package com.tycz.tweedle.lib.dtos.user.blocks

import kotlinx.serialization.Serializable

@Serializable
data class BlockedData(
    val description: String,
    val id: String,
    val name: String,
    val profile_image_url: String,
    val username: String
)
