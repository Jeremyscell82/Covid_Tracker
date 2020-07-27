package com.lloydsbyte.covidtracker.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.lloydsbyte.covidtracker.home.usa.UsaFragment
import com.lloydsbyte.covidtracker.home.world.WorldFragment


class HomeViewPagerAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {

    val homeScreens = listOf(
        WorldFragment(),
        UsaFragment()
    )

    override fun getItem(position: Int): Fragment {
        return homeScreens[position]
    }

    override fun getCount() = homeScreens.size

}