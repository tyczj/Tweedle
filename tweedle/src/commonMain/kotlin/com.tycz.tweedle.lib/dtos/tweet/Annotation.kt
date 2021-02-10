package com.tycz.tweedle.lib.dtos.tweet

import kotlinx.serialization.Serializable

@Serializable
data class Annotation(
    val end: Int? = null,
    val normalized_text: String? = null,
    val probability: Double? = null,
    val start: Int? = null,
    val type: String? = null
)