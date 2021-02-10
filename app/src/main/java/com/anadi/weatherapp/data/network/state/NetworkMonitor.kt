package com.anadi.weatherapp.data.network.state

import androidx.lifecycle.LiveData

interface NetworkMonitor {
    val isConnected: LiveData<Boolean>
    val online: Boolean
    val offline: Boolean
}
