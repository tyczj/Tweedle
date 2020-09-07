package com.tycz.tweedle.lib.dtos.tweet.rules

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Data class for sending a delete rule request
 */
@JsonClass(generateAdapter = true)
data class DeleteRule(
    @field:Json(name = "delete") val delete: Delete
)