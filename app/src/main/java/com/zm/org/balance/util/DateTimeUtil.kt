package com.zm.org.balance.util

import java.text.SimpleDateFormat
import java.util.*


/**
 * To restrict toDayTimeStampMillis logic only for Long TimeStampMillis
 */
@JvmInline
value class TimeMillis(val timeMillis: Long = System.currentTimeMillis()) {
    fun getDayStr(): String {

        val dateObj = Date(timeMillis)
        val dayNumberSuffix: String = getDayOfMonthSuffix(Calendar.getInstance().apply {
            time = dateObj
        }.get(Calendar.DAY_OF_MONTH))
        val format = "d'$dayNumberSuffix' MMMM, yyyy"
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(dateObj)
    }
}

/**
 * Convert the DateTime TimeStampMillis to DayOnly TimeStampMillis
 */
fun TimeMillis.toDayTimeStampMillis(): TimeMillis {
    val cal = Calendar.getInstance()
    cal.timeInMillis = this.timeMillis
    cal.timeZone = TimeZone.getDefault()
    cal.set(Calendar.HOUR_OF_DAY, 0)
    cal.set(Calendar.MINUTE, 0)
    cal.set(Calendar.SECOND, 0)
    cal.set(Calendar.MILLISECOND, 0)
    return TimeMillis(cal.timeInMillis)
}

private fun getDayOfMonthSuffix(dayOfTheMonth: Int): String {
    assert(dayOfTheMonth in 1..31) { "illegal day of month: $dayOfTheMonth" }
    return if (dayOfTheMonth in 11..13) {
        "th"
    } else when (dayOfTheMonth % 10) {
        1 -> "st"
        2 -> "nd"
        3 -> "rd"
        else -> "th"
    }
}
