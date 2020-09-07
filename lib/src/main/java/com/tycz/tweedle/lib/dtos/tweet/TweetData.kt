package com.tycz.tweedle.lib.dtos.tweet

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.tycz.tweedle.lib.dtos.shared.Attachments
import com.tycz.tweedle.lib.dtos.shared.Entities
import com.tycz.tweedle.lib.dtos.shared.Geo

/**
 * Tweets are the basic building block of all things Twitter. The Tweet object has a long list of ‘root-level’ fields,
 * such as id, text, and created_at. Tweet objects are also the ‘parent’ object to several child objects including user,
 * media, poll, and place. Use the field parameter tweet.fields when requesting these root-level fields on the Tweet object.
 *
 * The Tweet object that can be found and expanded in the user resource. Additional Tweets related to the requested
 * Tweet can also be found and expanded in the Tweet resource. The object is available for expansion with
 * ?expansions=pinned_tweet_id in the user resource or ?expansions=referenced_tweets.id in the Tweet resource to get
 * the object with only default fields. Use the expansion with the field parameter: tweet.fields when requesting
 * additional fields to complete the object.
 */
@JsonClass(generateAdapter = true)
data class TweetData(
    /**
     * Specifies the type of attachments (if any) present in this Tweet.
     */
    @field:Json(name = "attachments") val attachments: Attachments?,
    /**
     * The unique identifier of the User who posted this Tweet.
     */
    @field:Json(name = "author_id") val author_id: String?,
    /**
     * Contains context annotations for the Tweet.
     */
    @field:Json(name = "context_annotations") val context_annotations: List<ContextAnnotation>?,
    /**
     * Creation time of the Tweet.
     */
    @field:Json(name = "created_at") val created_at: String?,
    /**
     * Entities which have been parsed out of the text of the Tweet. Additionally see entities in Twitter Objects.
     */
    @field:Json(name = "entities") val entities: Entities?,
    /**
     * The unique identifier of the requested Tweet.
     */
    @field:Json(name = "id") val id: String,
    /**
     * If the represented Tweet is a reply, this field will contain the original Tweet’s author ID.
     * This will not necessarily always be the user directly mentioned in the Tweet.
     */
    @field:Json(name = "in_reply_to_user_id") val in_reply_to_user_id: String?,
    /**
     * Language of the Tweet, if detected by Twitter. Returned as a BCP47 language tag.
     */
    @field:Json(name = "lang") val lang: String?,
    /**
     * This field only surfaces when a Tweet contains a link. The meaning of the field doesn’t pertain to the Tweet
     * content itself, but instead it is an indicator that the URL contained in the Tweet may contain content or media
     * identified as sensitive content.
     */
    @field:Json(name = "possibly_sensitive") val possibly_sensitive: Boolean?,
    /**
     * Public engagement metrics for the Tweet at the time of the request.
     */
    @field:Json(name = "public_metrics") val public_metrics: PublicMetrics?,
    /**
     * A list of Tweets this Tweet refers to. For example, if the parent Tweet is a Retweet, a Retweet with comment
     * (also known as Quoted Tweet) or a Reply, it will include the related Tweet referenced to by its parent.
     */
    @field:Json(name = "referenced_tweets") val referenced_tweets: List<ReferencedTweet>?,
    /**
     * The name of the app the user Tweeted from.
     */
    @field:Json(name = "source") val source: String?,
    /**
     * The actual UTF-8 text of the Tweet. See twitter-text for details on what characters are currently considered valid.
     */
    @field:Json(name = "text") val text: String,
    /**
     * Contains details about the location tagged by the user in this Tweet, if they specified one.
     */
    @field:Json(name = "geo") val geo: Geo?,
    /**
     * Non-public engagement metrics for the Tweet at the time of the request.
     *
     * Requires user context authentication.
     */
    @field:Json(name = "non_public_metrics") val non_public_metrics: NonPublicMetrics?,
    /**
     * Engagement metrics, tracked in an organic context, for the Tweet at the time of the request.
     * Requires user context authentication.
     */
    @field:Json(name = "organic_metrics") val organic_metrics: OrganicMetrics?,
    /**
     * Engagement metrics, tracked in a promoted context, for the Tweet at the time of the request.
     * Requires user context authentication.
     */
    @field:Json(name = "promoted_metrics") val promoted_metrics: PromotedMetrics?,
    /**
     * When present, contains withholding details for
     */
    @field:Json(name = "withheld") val withheld: Withheld?
)