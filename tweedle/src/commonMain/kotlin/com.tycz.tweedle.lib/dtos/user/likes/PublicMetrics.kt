package com.tycz.tweedle.lib.dtos.user.likes

import kotlinx.serialization.Serializable

@Serializable
data class PublicMetrics(
    val followers_count: Int? = null,
    val following_count: Int? = null,
    val listed_count: Int? = null,
    val tweet_count: Int? = null
)