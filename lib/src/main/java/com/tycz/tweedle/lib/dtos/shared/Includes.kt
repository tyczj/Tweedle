package com.tycz.tweedle.lib.dtos.shared

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.tycz.tweedle.lib.dtos.media.Media
import com.tycz.tweedle.lib.dtos.user.User
import com.tycz.tweedle.lib.dtos.place.Place
import com.tycz.tweedle.lib.dtos.poll.Poll
import com.tycz.tweedle.lib.dtos.tweet.TweetData

@JsonClass(generateAdapter = true)
data class Includes(
    @field:Json(name = "tweets") val tweets: List<TweetData>?,
    @field:Json(name = "users") val users: List<User>?,
    @field:Json(name = "places") val places: List<Place>?,
    @field:Json(name = "media") val media: List<Media>?,
    @field:Json(name = "polls") val polls: List<Poll>?
)