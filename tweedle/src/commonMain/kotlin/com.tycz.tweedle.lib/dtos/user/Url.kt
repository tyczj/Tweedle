package com.tycz.tweedle.lib.dtos.user

import com.tycz.tweedle.lib.dtos.shared.UrlData
import kotlinx.serialization.Serializable

@Serializable
data class Url(
    val urls: List<UrlData>? = null
)