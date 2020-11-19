package com.tycz.tweedle.lib.dtos.tweet.rules

import com.tycz.tweedle.lib.dtos.tweet.Meta
import kotlinx.serialization.Serializable

/**
 * Data class for the delete rule request
 */
@Serializable
data class DeleteRuleResponse(
    val meta: Meta
)