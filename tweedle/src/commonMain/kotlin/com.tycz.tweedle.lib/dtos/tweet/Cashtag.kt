package com.tycz.tweedle.lib.dtos.tweet

import kotlinx.serialization.Serializable

@Serializable
data class Cashtag(
    val end: Int? = null,
    val start: Int? = null,
    val tag: String? = null
)