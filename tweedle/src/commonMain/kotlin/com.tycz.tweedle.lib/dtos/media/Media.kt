package com.tycz.tweedle.lib.dtos.media

import kotlinx.serialization.*

/**
 * Media refers to any image, GIF, or video attached to a Tweet. The media object is not a primary object on any
 * endpoint, but can be found and expanded in the Tweet object.

 * The object is available for expansion with ?expansions=attachments.media_keys to get the condensed object with only
 * default fields. Use the expansion with the field parameter: media.fields when requesting additional fields to complete
 * the object.
 */
@Serializable
data class Media(
    /**
     * Available when type is video. Duration in milliseconds of the video.
     */
    val duration_ms: Int? = null,
    /**
     * Height of this content in pixels.
     */
    val height: Int? = null,
    /**
     * Unique identifier of the expanded media content.
     */
    val media_key: String,
    /**
     * URL to the static placeholder preview of this content.
     */
    val preview_image_url: String? = null,
    /**
     * Public engagement metrics for the media content at the time of the request.
     */
    val public_metrics: PublicMetrics? = null,
    /**
     * Type of content (animated_gif, photo, video).
     */
    val type: String,
    /**
     * Width of this content in pixels.
     */
    val width: Int? = null,
    /**
     * Url of a photo image uploaded
     */
    val url:String? = null,
    /**
     * Engagement metrics for the media content, tracked in a promoted context, at the time of the request.
     * Requires user context authentication.
     */
    val promoted_metrics: PromotedMetrics? = null,
    /**
     * Engagement metrics for the media content, tracked in an organic context, at the time of the request.
     * Requires user context authentication.
     */
    val organic_metrics: OrganicMetrics? = null,
    /**
     * Non-public engagement metrics for the media content at the time of the request.
     * Requires user context authentication.
     */
    val non_public_metrics: NonPublicMetrics? = null
)