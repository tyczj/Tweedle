package com.tycz.tweedle.lib.dtos.tweet

import kotlinx.serialization.Serializable

@Serializable
data class PublicMetrics(
    val like_count: Int? = null,
    val quote_count: Int? = null,
    val reply_count: Int? = null,
    val retweet_count: Int? = null
)