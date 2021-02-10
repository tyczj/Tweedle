package com.tycz.tweedle.lib.dtos.shared

import com.tycz.tweedle.lib.dtos.tweet.Image
import kotlinx.serialization.Serializable

@Serializable
data class UrlData(
     val description: String? = null,
     val display_url: String? = null,
     val end: Int? = null,
     val expanded_url: String? = null,
     val images: List<Image>? = null,
     val start: Int? = null,
     val status: Int? = null,
     val title: String? = null,
     val unwound_url: String? = null,
     val url: String? = null
)