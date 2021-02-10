package com.tycz.tweedle.lib.dtos.tweet.rules

import com.tycz.tweedle.lib.dtos.tweet.Add
import kotlinx.serialization.Serializable

/**
 * Data object for adding rules
 */
@Serializable
data class Rule(
    /**
     * List of rules to add
     */
    val add: List<Add>
)