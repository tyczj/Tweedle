package com.tycz.tweedle.lib.dtos.tweet.rules

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.tycz.tweedle.lib.dtos.tweet.Error
import com.tycz.tweedle.lib.dtos.tweet.Meta

/**
 * Data class for the add rule request
 */
@JsonClass(generateAdapter = true)
data class RuleResponse(
    /**
     * Errors with the request
     */
    @field:Json(name = "errors") val errors: List<Error>?,
    @field:Json(name = "meta") val meta: Meta?
)