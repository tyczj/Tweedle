package com.tycz.tweedle.lib.dtos.tweet.rules

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.tycz.tweedle.lib.dtos.tweet.Meta

/**
 * Data class for getting the stream rules
 */
@JsonClass(generateAdapter = true)
data class StreamRulesResponse(
    /**
     * Rules
     */
    @field:Json(name = "data")val `data`: List<Data>,
    @field:Json(name = "meta") val meta: Meta
)