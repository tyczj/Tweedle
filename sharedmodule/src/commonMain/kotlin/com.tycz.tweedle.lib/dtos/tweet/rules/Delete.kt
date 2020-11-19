package com.tycz.tweedle.lib.dtos.tweet.rules

import kotlinx.serialization.Serializable

/**
 * Data class for deleting rules
 */
@Serializable
data class Delete(
    /**
     * List of rule values to delete
     */
    val values: List<String>
)