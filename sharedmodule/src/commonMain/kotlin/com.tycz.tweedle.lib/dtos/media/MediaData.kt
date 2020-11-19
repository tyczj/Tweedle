package com.tycz.tweedle.lib.dtos.media

import com.tycz.tweedle.lib.dtos.shared.Attachments
import kotlinx.serialization.Serializable

@Serializable
data class MediaData(
    val attachments: Attachments?,
    val id: String?,
    val text: String?
)