package com.lloydsbyte.covidtracker.utilz

import com.lloydsbyte.covidtracker.database.CountryModel
import timber.log.Timber
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat

class AppUtilz {
    companion object {
        fun calculateWorldCount(items: List<CountryModel>): String {
            var count = 0
            items.forEach {
                count += it.totalConfirmed
            }
            return insertCommas(count)
        }

        fun insertCommas(num: Int): String{
            return NumberFormat.getIntegerInstance().format(num).toString()
        }

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

        fun convertToPercentage(percentage: Float): String {
            Timber.d("JL_ convert to percentage $percentage")
            val df = DecimalFormat("##.#")
            df.roundingMode = RoundingMode.CEILING
            return "${df.format(percentage)}%"
        }
    }
}