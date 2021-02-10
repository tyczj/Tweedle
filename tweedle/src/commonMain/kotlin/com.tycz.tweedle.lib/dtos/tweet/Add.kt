package com.tycz.tweedle.lib.dtos.tweet

import kotlinx.serialization.Serializable

/**
 * Data class for
 */
@Serializable
data class Add(
    /**
     * Rule value
     */
    val value: String,
    /**
     * Attaches to a tweet to indicate what rule the tweet is associated with
     */
    val tag: String
)