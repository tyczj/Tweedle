package com.tycz.tweedle.lib.dtos.tweet

import kotlinx.serialization.Serializable

@Serializable
data class Entity(
    val description: String? = null,
    val id: String? = null,
    val name: String? = null
)