package com.tycz.tweedle.lib.dtos.shared

import kotlinx.serialization.Serializable

@Serializable
data class Hashtag(
    val end: Int? = null,
    val start: Int? = null,
    val tag: String? = null
)