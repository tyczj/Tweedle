package com.tycz.tweedle.lib.dtos.user.likes

import kotlinx.serialization.Serializable

@Serializable
data class Mention(
    val end: Int? = null,
    val start: Int? = null,
    val username: String? = null
)