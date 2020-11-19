package com.tycz.tweedle.lib.dtos.place

import kotlinx.serialization.Serializable

/**
 * The place tagged in a Tweet is not a primary object on any endpoint, but can be found and expanded in the Tweet resource.
 *
 * The object is available for expansion with ?expansions=geo.place_id to get the condensed object with only default fields.
 * Use the expansion with the field parameter: place.fields when requesting additional fields to complete the object.
 */
@Serializable
data class Place(
    /**
     * The full-length name of the country this place belongs to.
     */
    val country: String? = null,
    /**
     * The ISO Alpha-2 country code this place belongs to.
     */
    val country_code: String? = null,
    /**
     * A longer-form detailed place name.
     */
    val full_name: String,
    /**
     * Contains place details in GeoJSON format.
     */
    val geo: PlaceGeoData? = null,
    /**
     * The unique identifier of the expanded place, if this is a point of interest tagged in the Tweet.
     */
    val id: String,
    /**
     * The short name of this place.
     */
    val name: String? = null,
    /**
     * Specified the particular type of information represented by this place information, such as a city name, or a point of interest.
     */
    val place_type: String? = null
)