package com.depoza.util.timeperiod

import com.depoza.util.timeperiod.BasePeriod.MONTH_PERIOD
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test

class Last30DaysPeriodTest {

    private val period = Last30DayPeriod()

    @Test
    fun shouldBeOfMonthType() {
        assertThat(period.type, `is`(MONTH_PERIOD))
    }

    @Test
    fun shouldBeOfCorrectQuantity() {
        assertThat(period.quantity, `is`(-1))
    }

    @Test
    fun shouldBeStartedFromCurrentDay() {
        assertThat(period.lowerBound, `is`(0))
    }
}