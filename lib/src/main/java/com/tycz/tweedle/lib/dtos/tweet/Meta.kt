package com.tycz.tweedle.lib.dtos.tweet

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Meta(
    @field:Json(name = "sent") val sent: String?,
    @field:Json(name = "summary") val summary: Summary?,
    @field:Json(name = "newest_id") val newest_id: Long?,
    @field:Json(name = "oldest_id") val oldest_id: Long?,
    @field:Json(name = "result_count") val result_count: Int?,
    @field:Json(name = "next_token") val next_token: String?
)