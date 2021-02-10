package com.tycz.tweedle.lib.dtos.tweet

import com.tycz.tweedle.lib.dtos.shared.Includes
import kotlinx.serialization.Serializable

/**
 * SingleTweetPayload is the response payload for when getting a single tweet
 */
@Serializable
data class SingleTweetPayload (
    /**
     * Tweet data
     */
    val `data`: TweetData,
    val includes: Includes? = null
)