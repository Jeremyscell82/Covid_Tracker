package com.lloydsbyte.covidtracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.lloydsbyte.covidtracker.home.ViewPagerFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(
                    R.id.main_container,
                    ViewPagerFragment()
                )
                .commit()
        }
    }
}