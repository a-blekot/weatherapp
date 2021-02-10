package com.anadi.weatherapp.view.ui.mainactivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.observe
import by.kirich1409.viewbindingdelegate.viewBinding
import com.anadi.weatherapp.R
import com.anadi.weatherapp.data.network.state.NetworkMonitor
import com.anadi.weatherapp.databinding.MainActivityBinding
import com.anadi.weatherapp.view.ui.BaseActivity
import javax.inject.Inject

class MainActivity : BaseActivity(R.layout.main_activity) {

    private val binding: MainActivityBinding by viewBinding()

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    companion object {
        fun getIntent(context: Context) = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        networkMonitor.isConnected.observe(this) { onConnectionChanged(it) }
    }

    private fun onConnectionChanged(isConnected: Boolean) {
        binding.offlineMode.visibility = if (isConnected) GONE else VISIBLE
    }
}
