package com.tycz.tweedle.lib.dtos.tweet.rules

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.tycz.tweedle.lib.dtos.tweet.Meta

/**
 * Data class for the delete rule request
 */
@JsonClass(generateAdapter = true)
data class DeleteRuleResponse(
    @field:Json(name = "meta") val meta: Meta
)