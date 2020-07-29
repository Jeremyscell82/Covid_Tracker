package com.lloydsbyte.covidtracker.utilz

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.lloydsbyte.covidtracker.database.CountryModel
import com.lloydsbyte.covidtracker.database.StateModel
import timber.log.Timber
import java.math.RoundingMode
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class AppUtilz {
    companion object {

        //Check internet connection
        fun checkConnection(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
            return activeNetwork?.isConnectedOrConnecting == true
        }

        //Function used for Home screen toolbar title
        fun calculateWorldCount(items: List<CountryModel>): String {
            var count = 0
            items.forEach {
                count += it.totalConfirmed
            }
            return insertCommas(count)
        }

        fun calculateUsaCount(items: List<StateModel>): String {
            var count = 0
            items.forEach {
                count += it.totalConfirmed
            }
            return insertCommas(count)
        }

        //Used across the application
        fun insertCommas(num: Int): String {
            return NumberFormat.getIntegerInstance().format(num).toString()
        }

        //Used in Country & State bottom sheets
        fun abbreviateNumber(num: Int): String {
            val number = num.toString()
            Timber.d("JL_ abbreviate number... $number")
            return if (number.length > 6) {
                //Million
                val value = number.dropLast(6)
                val point = number.dropLast(5).last()
                "${value}.${point}M"
            } else if (number.length > 3) {
                val value = number.dropLast(3)
                val point = number.dropLast(2).last()
                "${value}.${point}k"
            } else {
                number
            }
        }

        //Used in Country & State bottom sheets
        fun convertToPercentage(percentage: Float): String {
            Timber.d("JL_ convert to percentage $percentage")
            val df = DecimalFormat("##.#")
            df.roundingMode = RoundingMode.CEILING
            return "${df.format(percentage)}%"
        }

        //Used in Country & State bottom sheets
        @SuppressLint("SimpleDateFormat")
        fun convertDate(dateStr: String): String? {
            val fromFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            fromFormat.isLenient = false
            val toFormat: DateFormat = SimpleDateFormat("EEE, MMM d, h:mm a")
            toFormat.isLenient = false
            val date: Date = fromFormat.parse(dateStr)
            println(toFormat.format(date))
            return toFormat.format(date ?: "")
        }

        //Show or Hide Keyboard
        fun showHideKeyboardForSearch(
            context: Context,
            showKeyboard: Boolean,
            editField: EditText?
        ) {
            val imm: InputMethodManager =
                context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            if (showKeyboard) {
                editField?.requestFocus()
                imm.showSoftInput(editField, InputMethodManager.SHOW_IMPLICIT)
            } else {
                imm.hideSoftInputFromWindow(editField?.windowToken, 0)
                editField?.clearFocus()
            }
        }

        fun getStateName(stateCode: String): String {
            return when (stateCode) {
                "AL" -> "Alabama"
                "AK" -> "Alaska"
                "AB" -> "Alberta"
                "AZ" -> "Arizona"
                "AR" -> "Arkansas"
                "BC" -> "British Columbia"
                "CA" -> "California"
                "CO" -> "Colorado"
                "CT" -> "Connecticut"
                "DE" -> "Delaware"
                "DC" -> "District Of Columbia"
                "FL" -> "Florida"
                "GA" -> "Georgia"
                "GU" -> "Guam"
                "HI" -> "Hawaii"
                "ID" -> "Idaho"
                "IL" -> "Illinois"
                "IN" -> "Indiana"
                "IA" -> "Iowa"
                "KS" -> "Kansas"
                "KY" -> "Kentucky"
                "LA" -> "Louisiana"
                "ME" -> "Maine"
                "MB" -> "Manitoba"
                "MD" -> "Maryland"
                "MA" -> "Massachusetts"
                "MI" -> "Michigan"
                "MN" -> "Minnesota"
                "MS" -> "Mississippi"
                "MO" -> "Missouri"
                "MT" -> "Montana"
                "NE" -> "Nebraska"
                "NV" -> "Nevada"
                "NB" -> "New Brunswick"
                "NH" -> "New Hampshire"
                "NJ" -> "New Jersey"
                "NM" -> "New Mexico"
                "NY" -> "New York"
                "NF" -> "Newfoundland"
                "NC" -> "North Carolina"
                "ND" -> "North Dakota"
                "NT" -> "Northwest Territories"
                "NS" -> "Nova Scotia"
                "NU" -> "Nunavut"
                "OH" -> "Ohio"
                "OK" -> "Oklahoma"
                "ON" -> "Ontario"
                "OR" -> "Oregon"
                "PA" -> "Pennsylvania"
                "PE" -> "Prince Edward Island"
                "PR" -> "Puerto Rico"
                "QC" -> "Quebec"
                "RI" -> "Rhode Island"
                "SK" -> "Saskatchewan"
                "SC" -> "South Carolina"
                "SD" -> "South Dakota"
                "TN" -> "Tennessee"
                "TX" -> "Texas"
                "UT" -> "Utah"
                "VT" -> "Vermont"
                "VI" -> "Virgin Islands"
                "VA" -> "Virginia"
                "WA" -> "Washington"
                "WV" -> "West Virginia"
                "WI" -> "Wisconsin"
                "WY" -> "Wyoming"
                "YT" -> "Yukon Territory"
                else -> "NaS"
            }
        }

    }
}