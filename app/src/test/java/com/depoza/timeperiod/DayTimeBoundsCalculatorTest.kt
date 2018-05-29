package com.depoza.timeperiod

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.util.*
import java.util.Calendar.*

@TestInstance(PER_CLASS)
class DayTimeBoundsCalculatorTest : BaseTimeBoundsCalculatorTest() {

    private val calculator = DayPeriod.DayTimeBoundsCalculator(timeProvider)
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
            "5, 4, 2018")
    fun curDay(day: Int, month: Int, year: Int) {
        val period = DayPeriod(0)
        val timeBounds = calculator.calculateFor(period)
        PeriodTimeBoundsVerifier(timeBounds)
                .lowerBound(day, month, year)
                .upperBound(day, month, year)
                .verify()
    }

    @ParameterizedTest
    @CsvSource(
            "-1, 4, 4, 2018",
            "-6, 30, 3, 2018",
            "-28, 8, 3, 2018")
    fun prevDay(quantity: Int, day: Int, month: Int, year: Int) {
        val period = DayPeriod(quantity)
        val timeBounds = calculator.calculateFor(period)
        PeriodTimeBoundsVerifier(timeBounds)
                .lowerBound(day, month, year)
                .upperBound(day, month, year)
                .verify()
    }

    @ParameterizedTest
    @CsvSource(
            "1, 6, 4, 2018",
            "6, 11, 4, 2018",
            "28, 3, 5, 2018")
    fun nextDay(quantity: Int, day: Int, month: Int, year: Int) {
        val period = DayPeriod(quantity)
        val timeBounds = calculator.calculateFor(period)
        PeriodTimeBoundsVerifier(timeBounds)
                .lowerBound(day, month, year)
                .upperBound(day, month, year)
                .verify()
    }
}