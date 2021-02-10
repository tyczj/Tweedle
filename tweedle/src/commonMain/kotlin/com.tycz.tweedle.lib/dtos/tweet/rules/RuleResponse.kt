package com.tycz.tweedle.lib.dtos.tweet.rules

import com.tycz.tweedle.lib.dtos.tweet.Error
import com.tycz.tweedle.lib.dtos.tweet.Meta
import kotlinx.serialization.Serializable

/**
 * Data class for the add rule request
 */
@Serializable
data class RuleResponse(
    /**
     * Errors with the request
     */
    val errors: List<Error>? = null,
    val meta: Meta? = null
)