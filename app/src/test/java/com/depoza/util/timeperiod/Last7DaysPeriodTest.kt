package com.depoza.util.timeperiod

import com.depoza.util.timeperiod.BasePeriod.WEEK_PERIOD
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test

class Last7DaysPeriodTest {

    private val period = Last7DayPeriod()

    @Test
    fun shouldBeOfWeekType() {
        assertThat(period.type, `is`(WEEK_PERIOD))
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