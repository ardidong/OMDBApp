package com.ardidong.omdbapp.presentation

import com.ardidong.omdbapp.common.ErrorEntity

sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val error: ErrorEntity) : UiState<Nothing>()
}
