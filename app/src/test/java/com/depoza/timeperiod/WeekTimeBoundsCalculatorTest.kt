package com.depoza.timeperiod

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.util.*
import java.util.Calendar.*

@TestInstance(PER_CLASS)
class WeekTimeBoundsCalculatorTest : BaseTimeBoundsCalculatorTest() {

    private val calculator = WeekPeriod.WeekTimeBoundsCalculator(timeProvider)
    private val cal5Apr2018 = Calendar.getInstance().apply {
        set(DAY_OF_MONTH, 5)
        set(MONTH, 3)
        set(YEAR, 2018)
        set(HOUR_OF_DAY, 13)
        set(MINUTE, 25)
        set(SECOND, 50)
        set(MILLISECOND, 55)
    }

    @BeforeAll
    fun setup() {
        provideTimeOf(cal5Apr2018)
    }

    @ParameterizedTest
    @CsvSource(
            "1, 2, 4, 2018, 8, 4, 2018,",
            "2, 3, 4, 2018, 9, 4, 2018,",
            "3, 4, 4, 2018, 10, 4, 2018,",
            "4, 5, 4, 2018, 11, 4, 2018,",
            "5, 30, 3, 2018, 5, 4, 2018,",
            "6, 31, 3, 2018, 6, 4, 2018,",
            "7, 1, 4, 2018, 7, 4, 2018,")
    fun curWeek(lowerBound: Int,
                startDay: Int, startMonth: Int, startYear: Int,
                endDay: Int, endMonth: Int, endYear: Int) {
        val period = WeekPeriod(lowerBound, 0)
        val timeBounds = calculator.calculateFor(period)
        PeriodTimeBoundsVerifier(timeBounds)
                .lowerBound(startDay, startMonth, startYear)
                .upperBound(endDay, endMonth, endYear)
                .verify()
    }

    @ParameterizedTest
    @CsvSource(
            "1, -1, 26, 3, 2018, 1, 4, 2018,",
            "2, -1, 27, 3, 2018, 2, 4, 2018,",
            "3, -2, 21, 3, 2018, 27, 3, 2018,",
            "4, -2, 22, 3, 2018, 28, 3, 2018,",
            "5, -3, 9, 3, 2018, 15, 3, 2018,",
            "6, -3, 10, 3, 2018, 16, 3, 2018,",
            "7, -3, 11, 3, 2018, 17, 3, 2018,")
    fun prevWeek(lowerBound: Int, quantity: Int,
                 startDay: Int, startMonth: Int, startYear: Int,
                 endDay: Int, endMonth: Int, endYear: Int) {
        val period = WeekPeriod(lowerBound, quantity)
        val timeBounds = calculator.calculateFor(period)
        PeriodTimeBoundsVerifier(timeBounds)
                .lowerBound(startDay, startMonth, startYear)
                .upperBound(endDay, endMonth, endYear)
                .verify()
    }

    @ParameterizedTest
    @CsvSource(
            "1, 1, 9, 4, 2018, 15, 4, 2018,",
            "2, 1, 10, 4, 2018, 16, 4, 2018,",
            "3, 2, 18, 4, 2018, 24, 4, 2018,",
            "4, 2, 19, 4, 2018, 25, 4, 2018,",
            "5, 3, 20, 4, 2018, 26, 4, 2018,",
            "6, 3, 21, 4, 2018, 27, 4, 2018,",
            "7, 3, 22, 4, 2018, 28, 4, 2018,")
    fun nextWeek(lowerBound: Int, quantity: Int,
                 startDay: Int, startMonth: Int, startYear: Int,
                 endDay: Int, endMonth: Int, endYear: Int) {
        val period = WeekPeriod(lowerBound, quantity)
        val timeBounds = calculator.calculateFor(period)
        PeriodTimeBoundsVerifier(timeBounds)
                .lowerBound(startDay, startMonth, startYear)
                .upperBound(endDay, endMonth, endYear)
                .verify()
    }
}