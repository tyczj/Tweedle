package com.tycz.tweedle.lib.dtos.shared

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Attachments(
    @field:Json(name = "media_keys") val media_keys: List<String>?,
    @field:Json(name = "poll_ids") val poll_ids: List<String>?
)