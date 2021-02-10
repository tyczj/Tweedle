package com.tycz.tweedle.lib.dtos.tweet

import kotlinx.serialization.Serializable

@Serializable
data class ContextAnnotation(
    val domain: Domain? = null,
    val entity: Entity? = null
)