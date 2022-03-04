package com.tycz.tweedle.lib.dtos.user.likes

import kotlinx.serialization.Serializable

@Serializable
data class Entities(
    val description: Description? = null,
    val url: UrlX? = null
)