package com.tycz.tweedle.lib.dtos.tweet

import kotlinx.serialization.Serializable

@Serializable
data class Withheld(
    val copyright: Boolean? = null,
    val country_codes: List<String>? = null,
    val scope: String? = null
)