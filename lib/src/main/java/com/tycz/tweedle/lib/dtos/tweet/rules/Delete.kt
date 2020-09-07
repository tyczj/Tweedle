package com.tycz.tweedle.lib.dtos.tweet.rules

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Data class for deleting rules
 */
@JsonClass(generateAdapter = true)
data class Delete(
    /**
     * List of rule values to delete
     */
    @field:Json(name = "values") val values: List<String>
)