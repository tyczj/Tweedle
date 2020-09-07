package com.tycz.tweedle.lib.dtos.user

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.tycz.tweedle.lib.dtos.shared.Hashtag

@JsonClass(generateAdapter = true)
data class Description(
    @field:Json(name = "hashtags") val hashtags: List<Hashtag>?
)