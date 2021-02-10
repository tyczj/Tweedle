package com.tycz.tweedle.lib.dtos.shared

import kotlinx.serialization.Serializable

@Serializable
data class Attachments(
    val media_keys: List<String>? = null,
    val poll_ids: List<String>? = null
)