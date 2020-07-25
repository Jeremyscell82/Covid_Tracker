package com.lloydsbyte.covidtracker.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lloydsbyte.covidtracker.MainActivity
import com.lloydsbyte.covidtracker.R
import com.rbddevs.splashy.Splashy
import timber.log.Timber

//This class only displays the background color prior to the app loading
class SplashActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(Timber.DebugTree())
        Splashy(this)
            .setLogo(R.mipmap.ic_launcher_foreground)
            .setTitle(R.string.app_name)
            .setTitleColor(R.color.primaryTextColor)
            .setSubTitle(R.string.splash_title)
            .setSubTitleColor(R.color.primaryTextColor)
            .setAnimation(Splashy.Animation.SLIDE_IN_TOP_BOTTOM, 1200)
            .setProgressColor(R.color.white)
            .setBackgroundResource(R.color.ic_launcher_background)
            .setFullScreen(true)
            .setDuration(2400)
            .show()
        Splashy.onComplete(object : Splashy.OnComplete {
            override fun onComplete() {
                launchApp()
            }

        })
    }

    private fun launchApp() {
        Timber.d("Launching App ...")
        val mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)
        overridePendingTransition(
            R.anim.fade_in,
            R.anim.fade_out
        )
        finish()
    }
}