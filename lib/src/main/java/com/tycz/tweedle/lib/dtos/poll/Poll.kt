package com.tycz.tweedle.lib.dtos.poll

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * A poll included in a Tweet is not a primary object on any endpoint, but can be found and expanded in the Tweet object.
 *
 * The object is available for expansion with ?expansions=attachments.poll_ids to get the condensed object with only default
 * fields. Use the expansion with the field parameter: poll.fields when requesting additional fields to complete the object.
 */
@JsonClass(generateAdapter = true)
data class Poll(
    /**
     * Specifies the total duration of this poll.
     */
    @field:Json(name = "duration_minutes") val duration_minutes: Int?,
    /**
     * Specifies the end date and time for this poll.
     */
    @field:Json(name = "end_datetime") val end_datetime: String?,
    /**
     * Unique identifier of the expanded poll.
     */
    @field:Json(name = "id") val id: String,
    /**
     * Contains objects describing each choice in the referenced poll.
     */
    @field:Json(name = "options") val options: List<Option>,
    /**
     * Indicates if this poll is still active and can receive votes, or if the voting is now closed.
     */
    @field:Json(name = "voting_status") val voting_status: String?
)