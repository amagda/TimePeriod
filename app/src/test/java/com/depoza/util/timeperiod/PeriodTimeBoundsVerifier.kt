package com.depoza.util.timeperiod

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import java.util.*
import java.util.Calendar.*

internal class PeriodTimeBoundsVerifier(private val timeBoundsToVerify: LongArray) {

    private var expectedStartDay: Int = -1
    private var expectedStartMonth: Int = -1
    private var expectedStartYear: Int = -1
    private var expectedStartHour: Int = -1
    private var expectedStartMinute: Int = -1
    private var expectedStartSec: Int = -1
    private var expectedStartMls: Int = -1

    private var expectedEndDay: Int = -1
    private var expectedEndMonth: Int = -1
    private var expectedEndYear: Int = -1
    private var expectedEndHour: Int = -1
    private var expectedEndMinute: Int = -1
    private var expectedEndSec: Int = -1
    private var expectedEndMls: Int = -1

    fun lowerBound(day: Int, month: Int, year: Int,
                   hour: Int = 0, minute: Int = 0, sec: Int = 0, mls: Int = 0): PeriodTimeBoundsVerifier {
        expectedStartDay = day
        expectedStartMonth = month
        expectedStartYear = year
        expectedStartHour = hour
        expectedStartMinute = minute
        expectedStartSec = sec
        expectedStartMls = mls
        return this
    }

    fun upperBound(day: Int, month: Int, year: Int,
                   hour: Int = 23, minute: Int = 59, sec: Int = 59, mls: Int = 999): PeriodTimeBoundsVerifier {
        expectedEndDay = day
        expectedEndMonth = month
        expectedEndYear = year
        expectedEndHour = hour
        expectedEndMinute = minute
        expectedEndSec = sec
        expectedEndMls = mls
        return this
    }

    fun verify() {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeBoundsToVerify[0]
        assertThat("invalid start day", calendar.get(DAY_OF_MONTH), `is`(expectedStartDay))
        assertThat("invalid start month", calendar.get(MONTH), `is`(expectedStartMonth - 1))
        assertThat("invalid start year", calendar.get(YEAR), `is`(expectedStartYear))
        assertThat("invalid start hour", calendar.get(HOUR_OF_DAY), `is`(expectedStartHour))
        assertThat("invalid start minute", calendar.get(MINUTE), `is`(expectedStartMinute))
        assertThat("invalid start seconds", calendar.get(SECOND), `is`(expectedStartSec))
        assertThat("invalid start milliseconds", calendar.get(MILLISECOND), `is`(expectedStartMls))

        calendar.timeInMillis = timeBoundsToVerify[1]
        assertThat("invalid end day", calendar.get(DAY_OF_MONTH), `is`(expectedEndDay))
        assertThat("invalid end month", calendar.get(MONTH), `is`(expectedEndMonth - 1))
        assertThat("invalid end year", calendar.get(YEAR), `is`(expectedEndYear))
        assertThat("invalid end hour", calendar.get(HOUR_OF_DAY), `is`(expectedEndHour))
        assertThat("invalid end minute", calendar.get(MINUTE), `is`(expectedEndMinute))
        assertThat("invalid end seconds", calendar.get(SECOND), `is`(expectedEndSec))
        assertThat("invalid end milliseconds", calendar.get(MILLISECOND), `is`(expectedEndMls))
    }
}