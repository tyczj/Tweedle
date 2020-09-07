package com.tycz.tweedle.lib.dtos.media

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.tycz.tweedle.lib.dtos.shared.Attachments

@JsonClass(generateAdapter = true)
data class MediaData(
    @field:Json(name = "attachments") val attachments: Attachments?,
    @field:Json(name = "id") val id: String?,
    @field:Json(name = "text") val text: String?
)