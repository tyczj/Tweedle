package com.tycz.tweedle.lib.dtos.poll

import com.tycz.tweedle.lib.dtos.shared.Attachments
import kotlinx.serialization.Serializable

/**
 * A poll included in a Tweet is not a primary object on any endpoint, but can be found and expanded in the Tweet object.
 *
 * The object is available for expansion with ?expansions=attachments.poll_ids to get the condensed object with only
 * default fields. Use the expansion with the field parameter: poll.fields when requesting additional fields to complete the object.
 */
@Serializable
data class PollData(
    val attachments: Attachments?,
    /**
     * Unique identifier of the expanded poll.
     */
    val id: String,
    val text: String?
)