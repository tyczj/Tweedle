package com.tycz.tweedle.lib.dtos.tweet

import com.tycz.tweedle.lib.dtos.shared.Includes
import kotlinx.serialization.Serializable

/**
 * MultipleTweetPayload is the response payload used in getting filtered tweets or recent tweets query
 */
@Serializable
data class MultipleTweetPayload(
    /**
     * A list of tweets returned
     */
    val `data`: List<TweetData>? = null,
    val includes: Includes? = null,
    val meta: Meta? = null
)