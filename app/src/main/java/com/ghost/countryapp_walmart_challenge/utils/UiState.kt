package com.ghost.countryapp_walmart_challenge.utils

sealed class UiState<out T> {
    data object Loading: UiState<Nothing>()
    data class Success<T>(val data: T): UiState<T>()
    data class Error(val message:String): UiState<Nothing>()
}