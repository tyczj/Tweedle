package com.tycz.tweedle.lib.dtos.tweet

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Entity(
    @field:Json(name = "description") val description: String?,
    @field:Json(name = "id") val id: String?,
    @field:Json(name = "name") val name: String?
)