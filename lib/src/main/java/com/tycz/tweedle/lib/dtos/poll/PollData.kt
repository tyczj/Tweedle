package com.tycz.tweedle.lib.dtos.poll

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.tycz.tweedle.lib.dtos.shared.Attachments

/**
 * A poll included in a Tweet is not a primary object on any endpoint, but can be found and expanded in the Tweet object.
 *
 * The object is available for expansion with ?expansions=attachments.poll_ids to get the condensed object with only
 * default fields. Use the expansion with the field parameter: poll.fields when requesting additional fields to complete the object.
 */
@JsonClass(generateAdapter = true)
data class PollData(
    @field:Json(name = "attachments") val attachments: Attachments?,
    /**
     * Unique identifier of the expanded poll.
     */
    @field:Json(name = "id") val id: String,
    @field:Json(name = "text") val text: String?
)