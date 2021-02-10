package com.tycz.tweedle.lib.dtos.tweet

import kotlinx.serialization.Serializable

@Serializable
data class Meta(
    val sent: String? = null,
    val summary: Summary? = null,
    val newest_id: Long? = null,
    val oldest_id: Long? = null,
    val result_count: Int? = null,
    val next_token: String? = null
)