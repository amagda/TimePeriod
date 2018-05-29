package com.depoza.timeperiod

import org.junit.jupiter.api.Test
import java.util.*
import java.util.Calendar.*

class CustomTimeBoundsCalculatorTest {

    private val calculator = CustomPeriod.CustomTimeBoundsCalculator()

    @Test
    fun calculate() {
        val calendar = Calendar.getInstance().apply {
            set(DAY_OF_MONTH, 5)
            set(MONTH, 3)
            set(YEAR, 2018)
            set(HOUR_OF_DAY, 13)
            set(MINUTE, 25)
            set(SECOND, 50)
            set(MILLISECOND, 55)
        }
        val startTime = calendar.timeInMillis

        calendar.apply {
            set(DAY_OF_MONTH, 11)
            set(MONTH, 3)
            set(YEAR, 2018)
            set(HOUR_OF_DAY, 20)
            set(MINUTE, 30)
            set(SECOND, 40)
            set(MILLISECOND, 50)
        }
        val endTime = calendar.timeInMillis

        val period = CustomPeriod(startTime, endTime)
        val timeBounds = calculator.calculateFor(period)
        PeriodTimeBoundsVerifier(timeBounds)
                .lowerBound(5, 4, 2018)
                .upperBound(11, 4, 2018)
                .verify()
    }
}