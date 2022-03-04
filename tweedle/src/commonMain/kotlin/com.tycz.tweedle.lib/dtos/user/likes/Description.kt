package com.tycz.tweedle.lib.dtos.user.likes

import kotlinx.serialization.Serializable

@Serializable
data class Description(
    val hashtags: List<Hashtag>? = null,
    val mentions: List<Mention>? = null,
    val urls: List<Url>? = null
)