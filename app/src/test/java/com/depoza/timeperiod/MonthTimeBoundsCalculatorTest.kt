package com.depoza.timeperiod

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.util.*
import java.util.Calendar.*

@TestInstance(PER_CLASS)
class MonthTimeBoundsCalculatorTest : BaseTimeBoundsCalculatorTest() {

    private val calculator = MonthPeriod.MonthTimeBoundsCalculator(timeProvider)
    private val cal5Apr2018 = Calendar.getInstance().apply {
        set(DAY_OF_MONTH, 5)
        set(MONTH, 3)
        set(YEAR, 2018)
        set(HOUR_OF_DAY, 13)
        set(MINUTE, 25)
        set(SECOND, 50)
        set(MILLISECOND, 55)
    }
    private val cal5Mar2018 = Calendar.getInstance().apply {
        set(DAY_OF_MONTH, 5)
        set(MONTH, 2)
        set(YEAR, 2018)
        set(HOUR_OF_DAY, 13)
        set(MINUTE, 25)
        set(SECOND, 50)
        set(MILLISECOND, 55)
    }

    @ParameterizedTest
    @CsvSource(
            "1, 1, 4, 2018, 30, 4, 2018",
            "2, 2, 4, 2018, 1, 5, 2018",
            "3, 3, 4, 2018, 2, 5, 2018",
            "4, 4, 4, 2018, 3, 5, 2018",
            "5, 5, 4, 2018, 4, 5, 2018",
            "6, 6, 3, 2018, 5, 4, 2018",
            "7, 7, 3, 2018, 6, 4, 2018",
            "8, 8, 3, 2018, 7, 4, 2018",
            "9, 9, 3, 2018, 8, 4, 2018",
            "10, 10, 3, 2018, 9, 4, 2018",
            "11, 11, 3, 2018, 10, 4, 2018",
            "12, 12, 3, 2018, 11, 4, 2018",
            "13, 13, 3, 2018, 12, 4, 2018",
            "14, 14, 3, 2018, 13, 4, 2018",
            "15, 15, 3, 2018, 14, 4, 2018",
            "16, 16, 3, 2018, 15, 4, 2018",
            "17, 17, 3, 2018, 16, 4, 2018",
            "18, 18, 3, 2018, 17, 4, 2018",
            "19, 19, 3, 2018, 18, 4, 2018",
            "20, 20, 3, 2018, 19, 4, 2018",
            "21, 21, 3, 2018, 20, 4, 2018",
            "22, 22, 3, 2018, 21, 4, 2018",
            "23, 23, 3, 2018, 22, 4, 2018",
            "24, 24, 3, 2018, 23, 4, 2018",
            "25, 25, 3, 2018, 24, 4, 2018",
            "26, 26, 3, 2018, 25, 4, 2018",
            "27, 27, 3, 2018, 26, 4, 2018",
            "28, 28, 3, 2018, 27, 4, 2018",
            "29, 29, 3, 2018, 28, 4, 2018",
            "30, 30, 3, 2018, 29, 4, 2018",
            "31, 31, 3, 2018, 30, 4, 2018"
    )
    fun curMonth(lowerBound: Int,
                 startDay: Int, startMonth: Int, startYear: Int,
                 endDay: Int, endMonth: Int, endYear: Int) {
        provideTimeOf(cal5Apr2018)
        val period = MonthPeriod(lowerBound, 0)
        val timeBounds = calculator.calculateFor(period)
        PeriodTimeBoundsVerifier(timeBounds)
                .lowerBound(startDay, startMonth, startYear)
                .upperBound(endDay, endMonth, endYear)
                .verify()
    }

    @ParameterizedTest
    @CsvSource(
            "1, -1, 1, 3, 2018, 31, 3, 2018",
            "2, -1, 2, 3, 2018, 1, 4, 2018",
            "3, -1, 3, 3, 2018, 2, 4, 2018",
            "4, -1, 4, 3, 2018, 3, 4, 2018",
            "5, -1, 5, 3, 2018, 4, 4, 2018",
            "6, -1, 6, 2, 2018, 5, 3, 2018",
            "7, -1, 7, 2, 2018, 6, 3, 2018",
            "8, -1, 8, 2, 2018, 7, 3, 2018",
            "9, -1, 9, 2, 2018, 8, 3, 2018",
            "10, -1, 10, 2, 2018, 9, 3, 2018",
            "11, -2, 11, 1, 2018, 10, 2, 2018",
            "12, -2, 12, 1, 2018, 11, 2, 2018",
            "13, -2, 13, 1, 2018, 12, 2, 2018",
            "14, -2, 14, 1, 2018, 13, 2, 2018",
            "15, -2, 15, 1, 2018, 14, 2, 2018",
            "16, -2, 16, 1, 2018, 15, 2, 2018",
            "17, -2, 17, 1, 2018, 16, 2, 2018",
            "18, -2, 18, 1, 2018, 17, 2, 2018",
            "19, -2, 19, 1, 2018, 18, 2, 2018",
            "20, -2, 20, 1, 2018, 19, 2, 2018",
            "21, -3, 21, 12, 2017, 20, 1, 2018",
            "22, -3, 22, 12, 2017, 21, 1, 2018",
            "23, -3, 23, 12, 2017, 22, 1, 2018",
            "24, -3, 24, 12, 2017, 23, 1, 2018",
            "25, -3, 25, 12, 2017, 24, 1, 2018",
            "26, -3, 26, 12, 2017, 25, 1, 2018",
            "27, -3, 27, 12, 2017, 26, 1, 2018",
            "28, -3, 28, 12, 2017, 27, 1, 2018",
            "29, -3, 29, 12, 2017, 28, 1, 2018",
            "30, -3, 30, 12, 2017, 29, 1, 2018",
            "31, -4, 30, 11, 2017, 29, 12, 2017"
    )
    fun prevMonth(lowerBound: Int, quantity: Int,
                  startDay: Int, startMonth: Int, startYear: Int,
                  endDay: Int, endMonth: Int, endYear: Int) {
        provideTimeOf(cal5Apr2018)
        val period = MonthPeriod(lowerBound, quantity)
        val timeBounds = calculator.calculateFor(period)
        PeriodTimeBoundsVerifier(timeBounds)
                .lowerBound(startDay, startMonth, startYear)
                .upperBound(endDay, endMonth, endYear)
                .verify()
    }

    @ParameterizedTest
    @CsvSource(
            "1, 1, 1, 5, 2018, 31, 5, 2018",
            "2, 1, 2, 5, 2018, 1, 6, 2018",
            "3, 1, 3, 5, 2018, 2, 6, 2018",
            "4, 1, 4, 5, 2018, 3, 6, 2018",
            "5, 1, 5, 5, 2018, 4, 6, 2018",
            "6, 1, 6, 4, 2018, 5, 5, 2018",
            "7, 1, 7, 4, 2018, 6, 5, 2018",
            "8, 1, 8, 4, 2018, 7, 5, 2018",
            "9, 1, 9, 4, 2018, 8, 5, 2018",
            "10, 1, 10, 4, 2018, 9, 5, 2018",
            "11, 2, 11, 5, 2018, 10, 6, 2018",
            "12, 2, 12, 5, 2018, 11, 6, 2018",
            "13, 2, 13, 5, 2018, 12, 6, 2018",
            "14, 2, 14, 5, 2018, 13, 6, 2018",
            "15, 2, 15, 5, 2018, 14, 6, 2018",
            "16, 2, 16, 5, 2018, 15, 6, 2018",
            "17, 2, 17, 5, 2018, 16, 6, 2018",
            "18, 2, 18, 5, 2018, 17, 6, 2018",
            "19, 2, 19, 5, 2018, 18, 6, 2018",
            "20, 2, 20, 5, 2018, 19, 6, 2018",
            "21, 3, 21, 6, 2018, 20, 7, 2018",
            "22, 3, 22, 6, 2018, 21, 7, 2018",
            "23, 3, 23, 6, 2018, 22, 7, 2018",
            "24, 3, 24, 6, 2018, 23, 7, 2018",
            "25, 3, 25, 6, 2018, 24, 7, 2018",
            "26, 3, 26, 6, 2018, 25, 7, 2018",
            "27, 3, 27, 6, 2018, 26, 7, 2018",
            "28, 3, 28, 6, 2018, 27, 7, 2018",
            "29, 3, 29, 6, 2018, 28, 7, 2018",
            "30, 3, 30, 6, 2018, 29, 7, 2018",
            "31, 4, 31, 7, 2018, 30, 8, 2018"
    )
    fun nextMonth(lowerBound: Int, quantity: Int,
                  startDay: Int, startMonth: Int, startYear: Int,
                  endDay: Int, endMonth: Int, endYear: Int) {
        provideTimeOf(cal5Apr2018)
        val period = MonthPeriod(lowerBound, quantity)
        val timeBounds = calculator.calculateFor(period)
        PeriodTimeBoundsVerifier(timeBounds)
                .lowerBound(startDay, startMonth, startYear)
                .upperBound(endDay, endMonth, endYear)
                .verify()
    }

    @Test
    fun curPeriod_LowerBound31_Now5Mar() {
        provideTimeOf(cal5Mar2018)
        val period = MonthPeriod(31, 0)
        val timeBounds = calculator.calculateFor(period)
        PeriodTimeBoundsVerifier(timeBounds)
                .lowerBound(28, 2, 2018)
                .upperBound(27, 3, 2018)
                .verify()
    }

    @Test
    fun prevPeriod_LowerBound31_Now5Mar() {
        provideTimeOf(cal5Mar2018)
        val period = MonthPeriod(31, -1)
        val timeBounds = calculator.calculateFor(period)
        PeriodTimeBoundsVerifier(timeBounds)
                .lowerBound(31, 1, 2018)
                .upperBound(28, 2, 2018)
                .verify()
    }

    @Test
    fun nextPeriod_LowerBound31_Now5Mar() {
        provideTimeOf(cal5Mar2018)
        val period = MonthPeriod(31, 1)
        val timeBounds = calculator.calculateFor(period)
        PeriodTimeBoundsVerifier(timeBounds)
                .lowerBound(31, 3, 2018)
                .upperBound(30, 4, 2018)
                .verify()
    }

    @Test
    fun prev30Days_Now1Mar() {
        val cal = Calendar.getInstance().apply {
            set(DAY_OF_MONTH, 1)
            set(MONTH, 2)
            set(YEAR, 2018)
            set(HOUR_OF_DAY, 13)
            set(MINUTE, 25)
            set(SECOND, 50)
            set(MILLISECOND, 55)
        }
        provideTimeOf(cal)
        val period = MonthPeriod(0, -1)
        val timeBounds = calculator.calculateFor(period)
        PeriodTimeBoundsVerifier(timeBounds)
                .lowerBound(30, 1, 2018)
                .upperBound(28, 2, 2018)
                .verify()
    }

    @Test
    fun prev30Days_Now5Mar() {
        provideTimeOf(cal5Mar2018)
        val period = MonthPeriod(0, -1)
        val timeBounds = calculator.calculateFor(period)
        PeriodTimeBoundsVerifier(timeBounds)
                .lowerBound(3, 2, 2018)
                .upperBound(4, 3, 2018)
                .verify()
    }

    @Test
    fun prev30Days_Now31Mar() {
        val cal = Calendar.getInstance().apply {
            set(DAY_OF_MONTH, 31)
            set(MONTH, 2)
            set(YEAR, 2018)
            set(HOUR_OF_DAY, 13)
            set(MINUTE, 25)
            set(SECOND, 50)
            set(MILLISECOND, 55)
        }
        provideTimeOf(cal)
        val period = MonthPeriod(0, -1)
        val timeBounds = calculator.calculateFor(period)
        PeriodTimeBoundsVerifier(timeBounds)
                .lowerBound(1, 3, 2018)
                .upperBound(30, 3, 2018)
                .verify()
    }
}