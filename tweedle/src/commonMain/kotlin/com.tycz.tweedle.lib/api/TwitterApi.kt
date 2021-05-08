package com.tycz.tweedle.lib.api

import com.tycz.tweedle.lib.engine
import io.ktor.client.HttpClient
import io.ktor.client.call.*
import io.ktor.client.features.*
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.client.request.get
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.Json

internal class TwitterClient private constructor() {

    companion object {
        const val BASE_URL = "https://api.twitter.com/2/"
        const val TWEETS_ENDPOINT = "tweets"
        const val USERS_ENDPOINT = "users"

        const val AUTH_BASE_URL = "https://api.twitter.com/oauth/"
        const val REQUEST_TOKEN_ENDPOINT = "request_token"
        const val ACCESS_TOKEN_ENDPOINT = "access_token"

        val instance: TwitterClient by lazy { TwitterClient() }
    }

    private val _client: HttpClient = HttpClient(engine) {
        install(JsonFeature) {
            val json = Json {
                this.isLenient = true
                this.ignoreUnknownKeys = true
            }
            serializer = KotlinxSerializer(json)
        }
        install(Logging){
//            this.level = LogLevel.ALL
            logger = Logger.DEFAULT
        }
    }

    suspend inline fun <reified T> get(builder: HttpRequestBuilder): T? {
        val response = _client.get<HttpResponse>(builder)

        when (response.status.value) {
            in 300..399 -> throw RedirectResponseException(response)
            in 400..499 -> throw ClientRequestException(response)
            in 500..599 -> throw ServerResponseException(response)
        }

        if (response.status.value >= 600) {
            throw ResponseException(response)
        }

        return response.receive<T>()
    }

    suspend fun getStream(builder: HttpRequestBuilder): HttpStatement {

        return _client.get<HttpStatement>(builder)

    }

    suspend inline fun <reified T> post(builder: HttpRequestBuilder): T? {
        val response = _client.post<HttpResponse>(builder)

        when (response.status.value) {
            in 300..399 -> throw RedirectResponseException(response)
            in 400..499 -> throw ClientRequestException(response)
            in 500..599 -> throw ServerResponseException(response)
        }

        if (response.status.value >= 600) {
            throw ResponseException(response)
        }

        return response.receive<T>()
    }

    suspend inline fun <reified T> put(builder: HttpRequestBuilder): T? {
        val response = _client.put<HttpResponse>(builder)

        when (response.status.value) {
            in 300..399 -> throw RedirectResponseException(response)
            in 400..499 -> throw ClientRequestException(response)
            in 500..599 -> throw ServerResponseException(response)
        }

        if (response.status.value >= 600) {
            throw ResponseException(response)
        }

        return response.receive<T>()
    }

    fun close() {
        _client.close()
    }

}