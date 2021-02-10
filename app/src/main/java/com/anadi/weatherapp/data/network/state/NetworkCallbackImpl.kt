package com.anadi.weatherapp.data.network.state

import android.net.ConnectivityManager
import android.net.LinkProperties
import android.net.Network
import android.net.NetworkCapabilities

class NetworkCallbackImpl(private val networkState: NetworkState) :
    ConnectivityManager.NetworkCallback() {
    override fun onAvailable(network: Network) {
        networkState.network = network
        networkState.isConnected.postValue(true)
    }

    override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
        networkState.networkCapabilities = networkCapabilities
    }

    override fun onLost(network: Network) {
        networkState.network = network
        networkState.isConnected.postValue(false)
    }

    override fun onLinkPropertiesChanged(network: Network, linkProperties: LinkProperties) {
        networkState.linkProperties = linkProperties
    }
}
