package com.tycz.tweedle.lib.dtos.place

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * The place tagged in a Tweet is not a primary object on any endpoint, but can be found and expanded in the Tweet resource.
 *
 * The object is available for expansion with ?expansions=geo.place_id to get the condensed object with only default fields.
 * Use the expansion with the field parameter: place.fields when requesting additional fields to complete the object.
 */
@JsonClass(generateAdapter = true)
data class Place(
    /**
     * The full-length name of the country this place belongs to.
     */
    @field:Json(name = "country") val country: String?,
    /**
     * The ISO Alpha-2 country code this place belongs to.
     */
    @field:Json(name = "country_code") val country_code: String?,
    /**
     * A longer-form detailed place name.
     */
    @field:Json(name = "full_name") val full_name: String,
    /**
     * Contains place details in GeoJSON format.
     */
    @field:Json(name = "geo") val geo: PlaceGeoData?,
    /**
     * The unique identifier of the expanded place, if this is a point of interest tagged in the Tweet.
     */
    @field:Json(name = "id") val id: String,
    /**
     * The short name of this place.
     */
    @field:Json(name = "name") val name: String?,
    /**
     * Specified the particular type of information represented by this place information, such as a city name, or a point of interest.
     */
    @field:Json(name = "place_type") val place_type: String?
)