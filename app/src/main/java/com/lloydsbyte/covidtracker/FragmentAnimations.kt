package com.lloydsbyte.covidtracker

import androidx.fragment.app.FragmentTransaction

class FragmentAnimations {

    enum class ANIMATION_KEY {
        SLIDE_IN_FROM_LEFT,
        SLIDE_IN_FROM_RIGHT,
        SLIDE_IN_FROM_TOP,
        SLIDE_IN_FROM_BOTTOM,
        FADE_IN,
        NO_ANIM,
        TEST_ANIM
    }

    fun addCustomAnimations(key: ANIMATION_KEY, ft: FragmentTransaction): FragmentTransaction {
        when (key) {
            ANIMATION_KEY.SLIDE_IN_FROM_LEFT -> {
                ft.setCustomAnimations(
                    R.anim.slide_in_right,
                    R.anim.slide_out_right,
                    R.anim.slide_in_left,
                    R.anim.slide_out_left
                )
            }
            ANIMATION_KEY.SLIDE_IN_FROM_RIGHT -> {
                ft.setCustomAnimations(
                    R.anim.slide_in_left,
                    R.anim.slide_out_left,
                    R.anim.slide_in_right,
                    R.anim.slide_out_right
                )
            }
            ANIMATION_KEY.SLIDE_IN_FROM_TOP -> {
                ft.setCustomAnimations(
                    R.anim.slide_in_down,
                    R.anim.slide_out_down,
                    R.anim.slide_in_up,
                    R.anim.slide_out_up
                )
            }
            ANIMATION_KEY.SLIDE_IN_FROM_BOTTOM -> {
                ft.setCustomAnimations(
                    R.anim.slide_in_up,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.slide_out_down
                )
            }
            ANIMATION_KEY.FADE_IN -> {
                ft.setCustomAnimations(
                    R.anim.fade_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.fade_out
                )
            }
            ANIMATION_KEY.TEST_ANIM -> {
                ft.setCustomAnimations(
                    android.R.anim.slide_in_left,
                    android.R.anim.fade_out,
                    android.R.anim.fade_in,
                    android.R.anim.slide_out_right
                )
            }
            ANIMATION_KEY.NO_ANIM -> {
                //Do nothing
            }
        }
        return ft
    }

}