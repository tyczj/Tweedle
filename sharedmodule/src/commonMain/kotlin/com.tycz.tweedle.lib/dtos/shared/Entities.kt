package com.tycz.tweedle.lib.dtos.shared

import com.tycz.tweedle.lib.dtos.tweet.Annotation
import com.tycz.tweedle.lib.dtos.tweet.Cashtag
import com.tycz.tweedle.lib.dtos.tweet.Mention
import com.tycz.tweedle.lib.dtos.user.Url
import kotlinx.serialization.Serializable

@Serializable
data class Entities(
    val annotations: List<Annotation>? = null,
    val cashtags: List<Cashtag>? = null,
    val hashtags: List<Hashtag>? = null,
    val mentions: List<Mention>? = null,
    val url: Url? = null,
    val urls: List<UrlData>? = null
)