package com.anadi.weatherinfo.ui.mainactivity

import android.os.Bundle
import com.anadi.weatherinfo.R
import com.anadi.weatherinfo.ui.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }
}
