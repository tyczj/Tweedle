package com.tycz.tweedle.lib.dtos.tweet.rules

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Summary of the request
 */
@JsonClass(generateAdapter = true)
data class Summary(
    /**
     * Number of rules deleted
     */
    @field:Json(name = "deleted") val deleted: Int,
    /**
     * Number of rules not deleted
     */
    @field:Json(name = "not_deleted") val not_deleted: Int
)