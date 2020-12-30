package com.anadi.weatherinfo.ui.details

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.anadi.weatherinfo.R
import com.anadi.weatherinfo.WeatherApplication
import com.anadi.weatherinfo.databinding.ActivityDetailsBinding
import com.anadi.weatherinfo.repository.IconMap
import timber.log.Timber
import java.text.DateFormat
import java.util.*
import javax.inject.Inject

class DetailsActivity : AppCompatActivity(R.layout.activity_details), DetailsContract.View {

    private val binding by viewBinding(ActivityDetailsBinding::bind, R.id.layout)
    
    @Inject
    lateinit var presenter: DetailsContract.Presenter
    private var id = 0

    override fun loading() {
        binding.progress.visibility = View.VISIBLE
    }

    override fun onError(resId: Int) {
        binding.progress.visibility = View.GONE
        Toast.makeText(applicationContext, getText(resId), Toast.LENGTH_LONG).show()
    }

    override fun onUpdateSuccess() {
        binding.progress.visibility = View.GONE
        val info = presenter.getInfo(id)
        if (info == null) {
            Timber.d("WeatherInfo is null!")
            return
        }
        binding.weatherIcon.setImageResource(IconMap.getIconId(info.weather[0].icon))
        binding.windDegreeIcon.rotation = info.wind.deg.toFloat()
        binding.locationName.text = getString(R.string.location_name, info.name, info.sys.country)
        var minutes = info.timezone / 60
        var hours = minutes / 60
        minutes %= 60 // to get right time in format hh:mm.
        val sign = if (hours > 0) "+" else if (hours < 0) "-" else ""
        hours = Math.abs(hours)
        binding.timezone.text = getString(R.string.timezone, sign, hours, minutes)
        binding.temp.text = getString(R.string.temp_celsium, info.main.temp)
        binding.windSpeed.text = getString(R.string.wind_speed_ms, info.wind.speed)
        binding.tempFeelsLike.text = getString(R.string.feels_like_celsium, info.main.feelsLike)
        binding.pressure.text = getString(R.string.pressure, info.main.pressure)
        binding.humidity.text = getString(R.string.humidity, info.main.humidity)

        //        SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
        val format = DateFormat.getTimeInstance()
        val sunrise = Date(info.sys.sunrise * 1000L)
        val sunset = Date(info.sys.sunset * 1000L)
        binding.sysSunrise.text = getString(R.string.sunrise, format.format(sunrise))
        binding.sysSunset.text = getString(R.string.sunset, format.format(sunset))
        binding.coordinates.text = getString(R.string.coordinates, info.coord.lat, info.coord.lon)
    }

    fun update(view: View?) {
        val animation = ObjectAnimator.ofFloat(binding.windSpeed, "rotation", binding.windSpeed.rotation - 720f)
        animation.duration = 500
        animation.start()
        createAnimation()
        presenter.update()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WeatherApplication.graph.inject(this)

        val intent = intent
        id = intent.getIntExtra("id", 0)
        presenter.view = this
        presenter.id = id
        presenter.subscribe()
    }

    override fun onDestroy() {
        presenter.unsubscribe()
        super.onDestroy()
    }

    private fun createAnimation() {
        when (binding.weatherIcon.visibility) {
            View.VISIBLE -> animationHideImage()
        }
    }

    private fun animationShowImage() {
        // Get the center for the clipping circle.
        val cx = binding.weatherIcon.width / 2
        val cy = binding.weatherIcon.height / 2

        // Get the final radius for the clipping circle.
        val finalRadius = Math.hypot(cx.toDouble(), cy.toDouble()).toFloat()

        // Create the animator for this view (the start radius is zero).
        val anim = ViewAnimationUtils.createCircularReveal(binding.weatherIcon, cx, cy, 0f, finalRadius)

        // Make the view visible and start the animation.
        binding.weatherIcon.visibility = View.VISIBLE
        anim.start()
    }

    private fun animationHideImage() {
        // Previously visible view

        // Get the center for the clipping circle.
        val cx = binding.weatherIcon.width / 2
        val cy = binding.weatherIcon.height / 2

        // Get the initial radius for the clipping circle.
        val initialRadius = Math.hypot(cx.toDouble(), cy.toDouble()).toFloat()

        // Create the animation (the final radius is zero.
        val anim = ViewAnimationUtils.createCircularReveal(binding.weatherIcon, cx, cy, initialRadius, 0f)

        // Make the view invisible when the animation is done.
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                binding.weatherIcon.visibility = View.INVISIBLE
                animationShowImage()
            }
        })

        // Start the animation.
        anim.start()
    }
}