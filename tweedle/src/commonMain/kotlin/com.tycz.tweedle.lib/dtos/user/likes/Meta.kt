package com.tycz.tweedle.lib.dtos.user.likes

import kotlinx.serialization.Serializable

@Serializable
data class Meta(
    val next_token: String? = null,
    val result_count: Int? = null
)