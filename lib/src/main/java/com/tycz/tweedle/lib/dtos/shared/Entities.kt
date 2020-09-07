package com.tycz.tweedle.lib.dtos.shared

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.tycz.tweedle.lib.dtos.tweet.Annotation
import com.tycz.tweedle.lib.dtos.tweet.Cashtag
import com.tycz.tweedle.lib.dtos.tweet.Mention
import com.tycz.tweedle.lib.dtos.user.Url

@JsonClass(generateAdapter = true)
data class Entities(
    @field:Json(name = "annotations") val annotations: List<Annotation>?,
    @field:Json(name = "cashtags") val cashtags: List<Cashtag>?,
    @field:Json(name = "hashtags") val hashtags: List<Hashtag>?,
    @field:Json(name = "mentions") val mentions: List<Mention>?,
    @field:Json(name = "url") val url: Url?,
    @field:Json(name = "urls") val urls: List<UrlData>?
)