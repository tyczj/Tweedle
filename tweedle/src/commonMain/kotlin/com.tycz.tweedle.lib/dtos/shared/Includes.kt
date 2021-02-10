package com.tycz.tweedle.lib.dtos.shared

import com.tycz.tweedle.lib.dtos.media.Media
import com.tycz.tweedle.lib.dtos.user.User
import com.tycz.tweedle.lib.dtos.place.Place
import com.tycz.tweedle.lib.dtos.poll.Poll
import com.tycz.tweedle.lib.dtos.tweet.TweetData
import kotlinx.serialization.Serializable

@Serializable
data class Includes(
    val tweets: List<TweetData>? = null,
    val users: List<User>? = null,
    val places: List<Place>? = null,
    val media: List<Media>? = null,
    val polls: List<Poll>? = null
)