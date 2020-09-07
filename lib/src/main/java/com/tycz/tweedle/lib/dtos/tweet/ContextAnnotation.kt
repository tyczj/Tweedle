package com.tycz.tweedle.lib.dtos.tweet

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ContextAnnotation(
    @field:Json(name = "domain") val domain: Domain?,
    @field:Json(name = "entity") val entity: Entity?
)