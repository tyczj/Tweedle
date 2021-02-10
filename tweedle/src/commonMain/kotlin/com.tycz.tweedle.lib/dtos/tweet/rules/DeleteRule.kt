package com.tycz.tweedle.lib.dtos.tweet.rules

import kotlinx.serialization.Serializable

/**
 * Data class for sending a delete rule request
 */
@Serializable
data class DeleteRule(
    val delete: Delete
)