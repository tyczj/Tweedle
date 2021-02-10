package com.tycz.tweedle.lib.dtos.tweet

import kotlinx.serialization.Serializable

@Serializable
data class NonPublicMetrics(
    val impression_count: Int? = null,
    val url_link_clicks: Int? = null,
    val user_profile_clicks: Int? = null
)