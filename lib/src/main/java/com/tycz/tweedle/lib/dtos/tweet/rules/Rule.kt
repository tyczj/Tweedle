package com.tycz.tweedle.lib.dtos.tweet.rules

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.tycz.tweedle.lib.dtos.tweet.Add

/**
 * Data object for adding rules
 */
@JsonClass(generateAdapter = true)
data class Rule(
    /**
     * List of rules to add
     */
    @field:Json(name = "add") val add: List<Add>
)