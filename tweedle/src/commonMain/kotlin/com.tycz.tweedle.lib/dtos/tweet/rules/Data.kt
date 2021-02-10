package com.tycz.tweedle.lib.dtos.tweet.rules

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val id: String,
    val tag: String,
    val value: String
)