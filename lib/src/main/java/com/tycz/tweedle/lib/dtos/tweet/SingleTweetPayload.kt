package com.tycz.tweedle.lib.dtos.tweet

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.tycz.tweedle.lib.dtos.shared.Includes

/**
 * SingleTweetPayload is the response payload for when getting a single tweet
 */
@JsonClass(generateAdapter = true)
data class SingleTweetPayload (
    /**
     * Tweet data
     */
    @field:Json(name = "data") val `data`: TweetData,
    @field:Json(name = "includes") val includes: Includes?
)