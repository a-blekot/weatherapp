package com.anadi.weatherinfo.data.network.state

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkRequest
import androidx.lifecycle.LiveData
import javax.inject.Inject

class NetworkMonitorImpl @Inject constructor(
        context: Context,
        private val networkState: NetworkState,
        private val networkCallback: ConnectivityManager.NetworkCallback
) : NetworkMonitor {

    init {
        (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).apply {
            registerNetworkCallback(NetworkRequest.Builder().build(), networkCallback)
        }
    }

    override val isConnected: LiveData<Boolean>
        get() = networkState.isConnected

    override val online: Boolean
        get() = networkState.isConnected.value ?: false

    override val offline: Boolean
        get() = !online
}
