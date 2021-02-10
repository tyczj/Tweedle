package com.tycz.tweedle.lib.dtos.tweet

import kotlinx.serialization.Serializable

@Serializable
data class Summary(
    val created: Int? = null,
    val invalid: Int? = null,
    val not_created: Int? = null,
    val valid: Int? = null
)