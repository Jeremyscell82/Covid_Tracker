package com.lloydsbyte.covidtracker.utilz

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.lloydsbyte.covidtracker.database.CountryModel
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

        //Used across the application
        fun insertCommas(num: Int): String{
            return NumberFormat.getIntegerInstance().format(num).toString()
        }

        //Used in Country & State bottom sheets
        fun abbreviateNumber(num: Int): String {
            val number = num.toString()
            Timber.d("JL_ abbreviate number... $number")
           return  if (number.length > 6){
               //Million
               var value = number.dropLast(6)
               val test = number.dropLast(5).last()
               "${value}.${test}M"
            }
            else if (number.length > 3){
               "${number.dropLast(3)}k"
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
            return toFormat.format(date?:"")
        }

        //Show or Hide Keyboard
        fun showHideKeyboardForSearch(context: Context, showKeyboard: Boolean, editField: EditText?) {
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
    }
}