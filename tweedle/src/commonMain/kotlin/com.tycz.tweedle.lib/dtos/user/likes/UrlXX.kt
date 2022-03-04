package com.tycz.tweedle.lib.dtos.user.likes

import kotlinx.serialization.Serializable

@Serializable
data class UrlXX(
    val display_url: String? = null,
    val end: Int? = null,
    val expanded_url: String? = null,
    val start: Int? = null,
    val url: String? = null
)