package com.lloydsbyte.covidtracker.splash

import android.app.Activity
import com.lloydsbyte.covidtracker.R
import com.rbddevs.splashy.Splashy

class SplashScreen {
    companion object {
        fun displaySplash(activity: Activity){
            Splashy(activity)
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
        }
    }
}