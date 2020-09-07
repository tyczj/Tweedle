package com.tycz.tweedle.lib.dtos.user

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.tycz.tweedle.lib.dtos.shared.UrlData

@JsonClass(generateAdapter = true)
data class Url(
    @field:Json(name = "urls") val urls: List<UrlData>?
)