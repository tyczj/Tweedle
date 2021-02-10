package com.tycz.tweedle.lib.dtos.tweet

import com.tycz.tweedle.lib.dtos.shared.Attachments
import com.tycz.tweedle.lib.dtos.shared.Entities
import com.tycz.tweedle.lib.dtos.shared.Geo
import kotlinx.serialization.Serializable

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
@Serializable
data class TweetData(
    /**
     * Specifies the type of attachments (if any) present in this Tweet.
     */
    val attachments: Attachments? = null,
    /**
     * The unique identifier of the User who posted this Tweet.
     */
    val author_id: String? = null,
    /**
     * Contains context annotations for the Tweet.
     */
    val context_annotations: List<ContextAnnotation>? = null,
    /**
     * Creation time of the Tweet.
     */
    val created_at: String? = null,
    /**
     * Entities which have been parsed out of the text of the Tweet. Additionally see entities in Twitter Objects.
     */
    val entities: Entities? = null,
    /**
     * The unique identifier of the requested Tweet.
     */
    val id: String,
    /**
     * If the represented Tweet is a reply, this field will contain the original Tweet’s author ID.
     * This will not necessarily always be the user directly mentioned in the Tweet.
     */
    val in_reply_to_user_id: String? = null,
    /**
     * Language of the Tweet, if detected by Twitter. Returned as a BCP47 language tag.
     */
    val lang: String? = null,
    /**
     * This field only surfaces when a Tweet contains a link. The meaning of the field doesn’t pertain to the Tweet
     * content itself, but instead it is an indicator that the URL contained in the Tweet may contain content or media
     * identified as sensitive content.
     */
    val possibly_sensitive: Boolean? = null,
    /**
     * Public engagement metrics for the Tweet at the time of the request.
     */
    val public_metrics: PublicMetrics? = null,
    /**
     * A list of Tweets this Tweet refers to. For example, if the parent Tweet is a Retweet, a Retweet with comment
     * (also known as Quoted Tweet) or a Reply, it will include the related Tweet referenced to by its parent.
     */
    val referenced_tweets: List<ReferencedTweet>? = null,
    /**
     * The name of the app the user Tweeted from.
     */
    val source: String? = null,
    /**
     * The actual UTF-8 text of the Tweet. See twitter-text for details on what characters are currently considered valid.
     */
    val text: String,
    /**
     * Contains details about the location tagged by the user in this Tweet, if they specified one.
     */
    val geo: Geo? = null,
    /**
     * Non-public engagement metrics for the Tweet at the time of the request.
     *
     * Requires user context authentication.
     */
    val non_public_metrics: NonPublicMetrics? = null,
    /**
     * Engagement metrics, tracked in an organic context, for the Tweet at the time of the request.
     * Requires user context authentication.
     */
    val organic_metrics: OrganicMetrics? = null,
    /**
     * Engagement metrics, tracked in a promoted context, for the Tweet at the time of the request.
     * Requires user context authentication.
     */
    val promoted_metrics: PromotedMetrics? = null,
    /**
     * When present, contains withholding details for
     */
    val withheld: Withheld? = null
)