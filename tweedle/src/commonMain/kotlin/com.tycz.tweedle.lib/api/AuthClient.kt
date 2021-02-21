package com.tycz.tweedle.lib.api

import com.tycz.tweedle.lib.engine
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

internal class AuthClient private constructor(){

    companion object {
        const val BASE_URL = "https://api.twitter.com/oauth/"
        const val REQUEST_TOKEN_ENDPOINT = "request_token"

        val instance: AuthClient by lazy { AuthClient() }
    }

    private val _client: HttpClient = HttpClient(engine) {
        install(JsonFeature) {
            val json = kotlinx.serialization.json.Json {
                this.isLenient = true
                this.ignoreUnknownKeys = true
            }
            serializer = KotlinxSerializer(json)
        }
    }

    suspend inline fun <reified T> post(requestBuilder: HttpRequestBuilder): T? {
        val response = _client.post<HttpResponse>(requestBuilder)

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
}