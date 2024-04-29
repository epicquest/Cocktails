package com.epicqueststudios.cocktails.presentation.models

sealed class SearchState<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : SearchState<T>(data)
    class Error<T>(message: String?) : SearchState<T>(null, message)
    class Loading<T>: SearchState<T>()

    class Idle<T>: SearchState<T>()
}