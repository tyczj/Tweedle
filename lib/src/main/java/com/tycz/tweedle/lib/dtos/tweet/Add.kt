package com.tycz.tweedle.lib.dtos.tweet

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Data class for
 */
@JsonClass(generateAdapter = true)
data class Add(
    /**
     * Rule value
     */
    @field:Json(name = "value") val value: String,
    /**
     * Attaches to a tweet to indicate what rule the tweet is associated with
     */
    @field:Json(name = "tag") val tag: String
)