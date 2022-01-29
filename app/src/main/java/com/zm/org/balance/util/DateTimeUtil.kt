package com.zm.org.balance.util

import androidx.room.ColumnInfo
import java.util.Calendar
import java.util.TimeZone


/**
 * To restrict toDayTimeStampMillis logic only for Long TimeStampMillis
 */
@JvmInline
value class TimeMillis(val timeMillis: Long = System.currentTimeMillis())

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
