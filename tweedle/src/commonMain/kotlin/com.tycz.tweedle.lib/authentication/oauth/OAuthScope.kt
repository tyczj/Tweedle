package com.tycz.tweedle.lib.authentication.oauth

sealed class OAuthScope{

    val value: String
        get() = when(this){
            is TweetWriteScope -> "tweet.write"
            is TweetReadScope -> "tweet.read"
            is TweetModerateWrite -> "tweet.moderate.write"
            is UserReadScope -> "users.read"
            is UserFollowsReadScope -> "follows.read"
            is UserFollowsWriteScope -> "follows.write"
            is UserMuteReadScope -> "mute.read"
            is UserMuteWriteScope -> "mute.write"
            is UserBlockReadScope -> "block.read"
            is UserBlockWriteScope -> "block.write"
            is OfflineAccessScope -> "offline.access"
        }

    /**
     * Tweet and Retweet for you.
     */
    object TweetWriteScope : OAuthScope()

    /**
     * All the Tweets you can view, including Tweets from protected accounts.
     */
    object TweetReadScope: OAuthScope()

    /**
     * Hide and unhide replies to your Tweets.
     */
    object TweetModerateWrite: OAuthScope()

    /**
     * Any account you can view, including protected accounts.
     */
    object UserReadScope: OAuthScope()

    /**
     * People who follow you and people who you follow.
     */
    object UserFollowsReadScope: OAuthScope()

    /**
     * Follow and unfollow people for you.
     */
    object UserFollowsWriteScope: OAuthScope()

    /**
     * Accounts you’ve muted.
     */
    object UserMuteReadScope: OAuthScope()

    /**
     * Mute and unmute accounts for you.
     */
    object UserMuteWriteScope: OAuthScope()

    /**
     * Accounts you’ve blocked.
     */
    object UserBlockReadScope: OAuthScope()

    /**
     * Block and unblock accounts for you.
     */
    object UserBlockWriteScope: OAuthScope()

    /**
     * Stay connected to your account until you revoke access.
     */
    object OfflineAccessScope: OAuthScope()
}

