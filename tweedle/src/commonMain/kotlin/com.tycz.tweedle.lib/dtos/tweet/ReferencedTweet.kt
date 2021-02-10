package com.tycz.tweedle.lib.dtos.tweet

import kotlinx.serialization.Serializable

@Serializable
data class ReferencedTweet(
    val id: String,
    val type: String? = null
)