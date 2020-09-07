package com.tycz.tweedle.lib.dtos.user

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.tycz.tweedle.lib.dtos.shared.Entities

/**
 * The user object contains Twitter user account metadata describing the referenced user. The user object is the
 * primary object returned in the User Lookup endpoint. When requesting additional user fields on this endpoint,
 * simply use the fields parameter user.fields.

 * The user object can also be found as a child object and expanded in the Tweet object. The object is available for
 * expansion with ?expansions=author_id or ?expansions=in_reply_to_user_id to get the condensed object with only default
 * fields. Use the expansion with the field parameter: user.fields when requesting additional fields to complete the object.
 */
@JsonClass(generateAdapter = true)
data class User(
    /**
     * The UTC datetime that the user account was created on Twitter.
     */
    @field:Json(name = "created_at") val created_at: String?,
    /**
     * The text of this user's profile description (also known as bio), if the user provided one.
     */
    @field:Json(name = "description") val description: String?,
    /**
     * Contains details about text that has a special meaning in the user's description.
     */
    @field:Json(name = "entities") val entities: Entities?,
    /**
     * The unique identifier of this user.
     */
    @field:Json(name = "id") val id: String,
    /**
     * The location specified in the user's profile, if the user provided one. As this is a freeform value,
     * it may not indicate a valid location, but it may be fuzzily evaluated when performing searches with location queries.
     */
    @field:Json(name = "location") val location: String?,
    /**
     * The name of the user, as they’ve defined it on their profile. Not necessarily a person’s name. Typically capped
     * at 50 characters, but subject to change.
     */
    @field:Json(name = "name") val name: String,
    /**
     * Unique identifier of this user's pinned Tweet.
     */
    @field:Json(name = "pinned_tweet_id") val pinned_tweet_id: String?,
    /**
     * The URL to the profile image for this user, as shown on the user's profile.
     */
    @field:Json(name = "profile_image_url") val profile_image_url: String?,
    /**
     * Indicates if this user has chosen to protect their Tweets (in other words, if this user's Tweets are private).
     */
    @field:Json(name = "protected") val `protected`: Boolean?,
    /**
     * The URL specified in the user's profile, if present.
     */
    @field:Json(name = "url") val url: String?,
    /**
     * The Twitter screen name, handle, or alias that this user identifies themselves with. Usernames are unique but
     * subject to change. Typically a maximum of 15 characters long, but some historical accounts may exist with longer names.
     */
    @field:Json(name = "username") val username: String,
    /**
     * Indicates if this user is a verified Twitter User.
     */
    @field:Json(name = "verified") val verified: Boolean?,
    /**
     * Contains details about activity for this user.
     */
    @field:Json(name = "public_metrics") val public_metrics: PublicMetrics?
)