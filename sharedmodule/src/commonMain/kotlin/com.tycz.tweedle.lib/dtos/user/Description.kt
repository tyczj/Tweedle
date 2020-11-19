package com.tycz.tweedle.lib.dtos.user

import com.tycz.tweedle.lib.dtos.shared.Hashtag
import kotlinx.serialization.Serializable

@Serializable
data class Description(
    val hashtags: List<Hashtag>?
)