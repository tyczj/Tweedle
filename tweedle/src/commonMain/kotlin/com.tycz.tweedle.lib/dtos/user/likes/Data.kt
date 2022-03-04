package com.tycz.tweedle.lib.dtos.user.likes

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val created_at: String? = null,
    val description: String? = null,
    val entities: Entities? = null,
    val id: String? = null,
    val location: String? = null,
    val name: String? = null,
    val pinned_tweet_id: String? = null,
    val profile_image_url: String? = null,
    val `protected`: Boolean? = null,
    val public_metrics: PublicMetrics? = null,
    val url: String? = null,
    val username: String? = null,
    val verified: Boolean? = null
)