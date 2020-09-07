package com.tycz.tweedle.lib.dtos.tweet

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Summary(
    @field:Json(name = "created") val created: Int?,
    @field:Json(name = "invalid") val invalid: Int?,
    @field:Json(name = "not_created") val not_created: Int?,
    @field:Json(name = "valid") val valid: Int?
)