package com.anadi.weatherinfo.view.ui.mainactivity

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import by.kirich1409.viewbindingdelegate.viewBinding
import com.anadi.weatherinfo.R
import com.anadi.weatherinfo.data.network.state.NetworkMonitor
import com.anadi.weatherinfo.databinding.MainActivityBinding
import com.anadi.weatherinfo.view.ui.BaseActivity
import com.anadi.weatherinfo.view.ui.notification.WeatherNotificationManager
import com.anadi.weatherinfo.view.ui.notification.buildNotificationsGroup
import javax.inject.Inject

class MainActivity : BaseActivity(R.layout.main_activity) {

    private val binding: MainActivityBinding by viewBinding()

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    @Inject
    lateinit var notificationManager: WeatherNotificationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        networkMonitor.isConnected.observe(this, { onConnectionChanged(it) })

        binding.notification.setOnClickListener { notificationManager.buildNotifications() }
    }

    private fun onConnectionChanged(isConnected: Boolean) {
        binding.offlineMode.visibility = if (isConnected) GONE else VISIBLE
    }
}
