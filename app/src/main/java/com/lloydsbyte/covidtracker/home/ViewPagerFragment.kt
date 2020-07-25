package com.lloydsbyte.covidtracker.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.lloydsbyte.covidtracker.R
import kotlinx.android.synthetic.main.fragment_viewpager.view.*

class ViewPagerFragment: Fragment() {

    private var screenPosition = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_viewpager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.apply {
            home_screen_viewpager.apply {
                adapter = ViewPagerAdapter(
                    requireActivity().supportFragmentManager
                )
                addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                    override fun onPageScrollStateChanged(state: Int) {
                    }

                    override fun onPageScrolled(
                        position: Int,
                        positionOffset: Float,
                        positionOffsetPixels: Int
                    ) {
                    }

                    override fun onPageSelected(position: Int) {
                        if (screenPosition != position) {
                            view.home_bottom_navbar.itemActiveIndex = position
                            screenPosition = position
                        }
                    }

                })
            }
            //On BottomNavBar select
            home_bottom_navbar.onItemSelected = {
                home_screen_viewpager.currentItem = it
            }
            home_bottom_navbar.onItemReselected = {
                //Do Nothing
            }
        }
    }
}