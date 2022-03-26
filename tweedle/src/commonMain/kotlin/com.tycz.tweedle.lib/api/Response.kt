package com.tycz.tweedle.lib.api

sealed class Response<out T> {
    data class Success<out T>(val data: T): Response<T>()
    data class Error(val exception: Exception) : Response<Nothing>()
}