package com.tycz.tweedle.lib.dtos.poll

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Option(
    @field:Json(name = "label") val label: String?,
    @field:Json(name = "position") val position: Int?,
    @field:Json(name = "votes") val votes: Int?
)