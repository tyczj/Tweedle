package com.tycz.tweedle.lib.dtos.media

import kotlinx.serialization.Serializable

@Serializable
data class OrganicMetrics(
    val playback_0_count: Int? = null,
    val playback_100_count: Int? = null,
    val playback_25_count: Int? = null,
    val playback_50_count: Int? = null,
    val playback_75_count: Int? = null,
    val view_count: Int? = null
)