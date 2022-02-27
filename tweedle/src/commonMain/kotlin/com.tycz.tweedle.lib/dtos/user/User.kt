package com.tycz.tweedle.lib.dtos.user

import com.tycz.tweedle.lib.dtos.shared.Entities
import kotlinx.serialization.Serializable

/**
 * The user object contains Twitter user account metadata describing the referenced user. The user object is the
 * primary object returned in the User Lookup endpoint. When requesting additional user fields on this endpoint,
 * simply use the fields parameter user.fields.

 * The user object can also be found as a child object and expanded in the Tweet object. The object is available for
 * expansion with ?expansions=author_id or ?expansions=in_reply_to_user_id to get the condensed object with only default
 * fields. Use the expansion with the field parameter: user.fields when requesting additional fields to complete the object.
 */
@Serializable
data class User(
    /**
     * The UTC datetime that the user account was created on Twitter.
     */
    val created_at: String? = null,
    /**
     * The text of this user's profile description (also known as bio), if the user provided one.
     */
    val description: String? = null,
    /**
     * Contains details about text that has a special meaning in the user's description.
     */
    val entities: Entities? = null,
    /**
     * The unique identifier of this user.
     */
    val id: String? = null,
    /**
     * The location specified in the user's profile, if the user provided one. As this is a freeform value,
     * it may not indicate a valid location, but it may be fuzzily evaluated when performing searches with location queries.
     */
    val location: String? = null,
    /**
     * The name of the user, as they’ve defined it on their profile. Not necessarily a person’s name. Typically capped
     * at 50 characters, but subject to change.
     */
    val name: String,
    /**
     * Unique identifier of this user's pinned Tweet.
     */
    val pinned_tweet_id: String? = null,
    /**
     * The URL to the profile image for this user, as shown on the user's profile.
     */
    val profile_image_url: String? = null,
    /**
     * Indicates if this user has chosen to protect their Tweets (in other words, if this user's Tweets are private).
     */
    val `protected`: Boolean? = null,
    /**
     * The URL specified in the user's profile, if present.
     */
    val url: String? = null,
    /**
     * The Twitter screen name, handle, or alias that this user identifies themselves with. Usernames are unique but
     * subject to change. Typically a maximum of 15 characters long, but some historical accounts may exist with longer names.
     */
    val username: String,
    /**
     * Indicates if this user is a verified Twitter User.
     */
    val verified: Boolean? = null,
    /**
     * Contains details about activity for this user.
     */
    val public_metrics: PublicMetrics? = null
)