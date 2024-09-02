package com.ardidong.omdbapp.presentation.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalContext
import com.ardidong.omdbapp.common.orFalse
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

val Context.currentConnectivityState: ConnectionState
    get() {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return getCurrentConnectivityState(connectivityManager)
    }

fun networkCallback(callback: (ConnectionState) -> Unit) : NetworkCallback =
    object : NetworkCallback(){
        private var lastState: ConnectionState? = null

        override fun onAvailable(network: Network) {
            handleNetworkState(ConnectionState.Available)
        }

        override fun onLost(network: Network) {
            handleNetworkState(ConnectionState.Unavailable)
        }

        private fun handleNetworkState(state: ConnectionState) {
            if (lastState != state) {
                lastState = state
                callback(state)
            }
        }
    }

private fun getCurrentConnectivityState(
    connectivityManager: ConnectivityManager
): ConnectionState {
    val activeNetwork = connectivityManager.activeNetwork
    val isConnected = connectivityManager.getNetworkCapabilities(activeNetwork)
        ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).orFalse()

    return if (isConnected) ConnectionState.Available else ConnectionState.Unavailable
}

fun Context.observeConnectivityAsFlow() = callbackFlow {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val callback = networkCallback { connectionState -> trySend(connectionState) }

    val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .build()

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        connectivityManager.registerDefaultNetworkCallback(callback)
    } else {
        connectivityManager.registerNetworkCallback(networkRequest, callback)
    }

    val currentState = getCurrentConnectivityState(connectivityManager)
    trySend(currentState)

    awaitClose {
        connectivityManager.unregisterNetworkCallback(callback)
    }
}

@Composable
fun rememberConnectionState(): State<ConnectionState> {
    val context = LocalContext.current

    return produceState(initialValue = context.currentConnectivityState) {
        context.observeConnectivityAsFlow().collect{
            value = it
        }
    }
}
