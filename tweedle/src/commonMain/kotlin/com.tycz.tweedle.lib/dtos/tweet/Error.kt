package com.tycz.tweedle.lib.dtos.tweet

import kotlinx.serialization.Serializable

@Serializable
data class Error(
    val details: List<String>? = null,
    val title: String? = null,
    val type: String? = null,
    val value: String? = null
)