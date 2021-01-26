package com.anadi.weatherinfo.data.network.state

import android.net.LinkProperties
import android.net.Network
import android.net.NetworkCapabilities
import androidx.lifecycle.MutableLiveData

interface NetworkState {
    val isConnected: MutableLiveData<Boolean>
    var network: Network?
    var networkCapabilities: NetworkCapabilities?
    var linkProperties: LinkProperties?
}