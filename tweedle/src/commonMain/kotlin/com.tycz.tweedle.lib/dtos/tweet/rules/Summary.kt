package com.tycz.tweedle.lib.dtos.tweet.rules

import kotlinx.serialization.Serializable

/**
 * Summary of the request
 */
@Serializable
data class Summary(
    /**
     * Number of rules deleted
     */
    val deleted: Int,
    /**
     * Number of rules not deleted
     */
    val not_deleted: Int
)