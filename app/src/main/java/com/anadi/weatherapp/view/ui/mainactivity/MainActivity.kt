package com.anadi.weatherapp.view.ui.mainactivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.observe
import com.anadi.weatherapp.R
import com.anadi.weatherapp.data.network.state.NetworkMonitor
import com.anadi.weatherapp.databinding.MainActivityBinding
import com.anadi.weatherapp.view.ui.BaseActivity
import javax.inject.Inject

class MainActivity : BaseActivity(R.layout.main_activity) {

    private var binding: MainActivityBinding? = null

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    companion object {
        fun getIntent(context: Context) = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }

        networkMonitor.isConnected.observe(this) { onConnectionChanged(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun onConnectionChanged(isConnected: Boolean) {
        binding?.offlineMode?.isVisible = !isConnected
    }
}
