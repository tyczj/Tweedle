package com.tycz.tweedle.lib.api.interfaces

import com.tycz.tweedle.lib.dtos.user.Payload
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

internal interface IUserLookup {

    @GET("users/{id}")
    suspend fun getUser(@Header("Authorization") token:String, @Path("id") id:Long):Payload

    @GET("users")
    suspend fun getUsers(@Header("Authorization") token:String, @Query("ids") ids:String):List<Payload>

    @GET("users/by/username/{username}")
    suspend fun getUserByUsername(@Header("Authorization") token:String, @Path("username") username:String):Payload

    @GET("users/by")
    suspend fun getUsersByUsernames(@Header("Authorization") token:String, @Query("usernames") usernames:String):List<Payload>
}