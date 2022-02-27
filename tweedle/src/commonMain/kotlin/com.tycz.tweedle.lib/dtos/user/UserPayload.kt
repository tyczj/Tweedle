package com.tycz.tweedle.lib.dtos.user

import kotlinx.serialization.Serializable

@Serializable
data class UserPayload(
    val `data`: User
)
