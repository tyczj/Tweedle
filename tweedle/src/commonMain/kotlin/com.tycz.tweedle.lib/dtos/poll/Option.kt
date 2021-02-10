package com.tycz.tweedle.lib.dtos.poll

import kotlinx.serialization.Serializable

@Serializable
data class Option(
    val label: String? = null,
    val position: Int? = null,
    val votes: Int? = null
)