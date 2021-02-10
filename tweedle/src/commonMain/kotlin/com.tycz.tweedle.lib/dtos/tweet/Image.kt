package com.tycz.tweedle.lib.dtos.tweet

import kotlinx.serialization.Serializable

@Serializable
data class Image(
    val height: Int?,
    val url: String?,
    val width: Int?
)