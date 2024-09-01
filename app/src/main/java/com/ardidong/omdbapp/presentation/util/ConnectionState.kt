package com.ardidong.omdbapp.presentation.util

sealed class ConnectionState {
    data object Available : ConnectionState()
    data object Unavailable : ConnectionState()
}