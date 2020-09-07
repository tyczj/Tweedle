package com.tycz.tweedle.lib.dtos.media

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Media refers to any image, GIF, or video attached to a Tweet. The media object is not a primary object on any
 * endpoint, but can be found and expanded in the Tweet object.

 * The object is available for expansion with ?expansions=attachments.media_keys to get the condensed object with only
 * default fields. Use the expansion with the field parameter: media.fields when requesting additional fields to complete
 * the object.
 */
@JsonClass(generateAdapter = true)
data class Media(
    /**
     * Available when type is video. Duration in milliseconds of the video.
     */
    @field:Json(name = "duration_ms") val duration_ms: Int?,
    /**
     * Height of this content in pixels.
     */
    @field:Json(name = "height") val height: Int?,
    /**
     * Unique identifier of the expanded media content.
     */
    @field:Json(name = "media_key") val media_key: String,
    /**
     * URL to the static placeholder preview of this content.
     */
    @field:Json(name = "preview_image_url") val preview_image_url: String?,
    /**
     * Public engagement metrics for the media content at the time of the request.
     */
    @field:Json(name = "public_metrics") val public_metrics: PublicMetrics?,
    /**
     * Type of content (animated_gif, photo, video).
     */
    @field:Json(name = "type") val type: String,
    /**
     * Width of this content in pixels.
     */
    @field:Json(name = "width") val width: Int?,
    /**
     * Url of a photo image uploaded
     */
    @field:Json(name = "url") val url:String?,
    /**
     * Engagement metrics for the media content, tracked in a promoted context, at the time of the request.
     * Requires user context authentication.
     */
    @field:Json(name = "promoted_metrics") val promoted_metrics: PromotedMetrics?,
    /**
     * Engagement metrics for the media content, tracked in an organic context, at the time of the request.
     * Requires user context authentication.
     */
    @field:Json(name = "organic_metrics") val organic_metrics: OrganicMetrics?,
    /**
     * Non-public engagement metrics for the media content at the time of the request.
     * Requires user context authentication.
     */
    @field:Json(name = "non_public_metrics") val non_public_metrics: NonPublicMetrics?
)