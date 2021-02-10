package com.tycz.tweedle.lib.dtos.tweet.rules

import com.tycz.tweedle.lib.dtos.tweet.Meta
import kotlinx.serialization.Serializable

/**
 * Data class for getting the stream rules
 */
@Serializable
data class StreamRulesResponse(
    /**
     * Rules
     */
    val `data`: List<Data>,
    val meta: Meta
)