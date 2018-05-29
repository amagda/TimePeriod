package com.depoza.timeperiod

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.util.*
import java.util.Calendar.*

@TestInstance(PER_CLASS)
class YearTimeBoundsCalculatorTest : BaseTimeBoundsCalculatorTest() {

    private val calculator = YearPeriod.YearTimeBoundsCalculator(timeProvider)
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
            "1, 1, 1, 2018, 31, 12, 2018,",
            "2, 1, 2, 2018, 31, 1, 2019,",
            "3, 1, 3, 2018, 28, 2, 2019,",
            "4, 1, 4, 2018, 31, 3, 2019,",
            "5, 1, 5, 2017, 30, 4, 2018,",
            "6, 1, 6, 2017, 31, 5, 2018,",
            "7, 1, 7, 2017, 30, 6, 2018,",
            "8, 1, 8, 2017, 31, 7, 2018,",
            "9, 1, 9, 2017, 31, 8, 2018,",
            "10, 1, 10, 2017, 30, 9, 2018,",
            "11, 1, 11, 2017, 31, 10, 2018,",
            "12, 1, 12, 2017, 30, 11, 2018,")
    fun curYear(lowerBound: Int,
                startDay: Int, startMonth: Int, startYear: Int,
                endDay: Int, endMonth: Int, endYear: Int) {
        val period = YearPeriod(lowerBound, 0)
        val timeBounds = calculator.calculateFor(period)
        PeriodTimeBoundsVerifier(timeBounds)
                .lowerBound(startDay, startMonth, startYear)
                .upperBound(endDay, endMonth, endYear)
                .verify()
    }

    @ParameterizedTest
    @CsvSource(
            "1, -1, 1, 1, 2017, 31, 12, 2017,",
            "2, -1, 1, 2, 2017, 31, 1, 2018,",
            "3, -1, 1, 3, 2017, 28, 2, 2018,",
            "4, -2, 1, 4, 2016, 31, 3, 2017,",
            "5, -2, 1, 5, 2015, 30, 4, 2016,",
            "6, -2, 1, 6, 2015, 31, 5, 2016,",
            "7, -3, 1, 7, 2014, 30, 6, 2015,",
            "8, -3, 1, 8, 2014, 31, 7, 2015,",
            "9, -3, 1, 9, 2014, 31, 8, 2015,",
            "10, -4, 1, 10, 2013, 30, 9, 2014,",
            "11, -4, 1, 11, 2013, 31, 10, 2014,",
            "12, -4, 1, 12, 2013, 30, 11, 2014,")
    fun prevYear(lowerBound: Int, quantity: Int,
                 startDay: Int, startMonth: Int, startYear: Int,
                 endDay: Int, endMonth: Int, endYear: Int) {
        val period = YearPeriod(lowerBound, quantity)
        val timeBounds = calculator.calculateFor(period)
        PeriodTimeBoundsVerifier(timeBounds)
                .lowerBound(startDay, startMonth, startYear)
                .upperBound(endDay, endMonth, endYear)
                .verify()
    }

    @ParameterizedTest
    @CsvSource(
            "1, 1, 1, 1, 2019, 31, 12, 2019,",
            "2, 1, 1, 2, 2019, 31, 1, 2020,",
            "3, 1, 1, 3, 2019, 29, 2, 2020,",
            "4, 2, 1, 4, 2020, 31, 3, 2021,",
            "5, 2, 1, 5, 2019, 30, 4, 2020,",
            "6, 2, 1, 6, 2019, 31, 5, 2020,",
            "7, 3, 1, 7, 2020, 30, 6, 2021,",
            "8, 3, 1, 8, 2020, 31, 7, 2021,",
            "9, 3, 1, 9, 2020, 31, 8, 2021,",
            "10, 4, 1, 10, 2021, 30, 9, 2022,",
            "11, 4, 1, 11, 2021, 31, 10, 2022,",
            "12, 4, 1, 12, 2021, 30, 11, 2022,")
    fun nextYear(lowerBound: Int, quantity: Int,
                 startDay: Int, startMonth: Int, startYear: Int,
                 endDay: Int, endMonth: Int, endYear: Int) {
        val period = YearPeriod(lowerBound, quantity)
        val timeBounds = calculator.calculateFor(period)
        PeriodTimeBoundsVerifier(timeBounds)
                .lowerBound(startDay, startMonth, startYear)
                .upperBound(endDay, endMonth, endYear)
                .verify()
    }
}