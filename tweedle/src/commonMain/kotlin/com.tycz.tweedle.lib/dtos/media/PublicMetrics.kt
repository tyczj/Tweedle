package com.tycz.tweedle.lib.dtos.media

import kotlinx.serialization.Serializable

@Serializable
data class PublicMetrics(
    val view_count: Int? = null
)