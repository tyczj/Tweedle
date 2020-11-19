package com.tycz.tweedle.lib.dtos.poll

import kotlinx.serialization.Serializable

/**
 * A poll included in a Tweet is not a primary object on any endpoint, but can be found and expanded in the Tweet object.
 *
 * The object is available for expansion with ?expansions=attachments.poll_ids to get the condensed object with only default
 * fields. Use the expansion with the field parameter: poll.fields when requesting additional fields to complete the object.
 */
@Serializable
data class Poll(
    /**
     * Specifies the total duration of this poll.
     */
    val duration_minutes: Int? = null,
    /**
     * Specifies the end date and time for this poll.
     */
    val end_datetime: String? = null,
    /**
     * Unique identifier of the expanded poll.
     */
    val id: String,
    /**
     * Contains objects describing each choice in the referenced poll.
     */
    val options: List<Option>,
    /**
     * Indicates if this poll is still active and can receive votes, or if the voting is now closed.
     */
    val voting_status: String? = null
)