package com.tycz.tweedle.lib.dtos.tweet

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.tycz.tweedle.lib.dtos.shared.Includes

/**
 * MultipleTweetPayload is the response payload used in getting filtered tweets or recent tweets query
 */
@JsonClass(generateAdapter = true)
data class MultipleTweetPayload(
    /**
     * A list of tweets returned
     */
    @field:Json(name = "data") val `data`: List<TweetData>,
    @field:Json(name = "includes") val includes: Includes?,
    @field:Json(name = "meta") val meta: Meta?
)