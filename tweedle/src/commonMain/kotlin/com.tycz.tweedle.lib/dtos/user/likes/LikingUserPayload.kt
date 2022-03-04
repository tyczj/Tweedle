package com.tycz.tweedle.lib.dtos.user.likes

import kotlinx.serialization.Serializable

@Serializable
data class LikingUserPayload(
    val `data`: List<Data>,
    val meta: Meta? = null
)