package com.depoza.util.timeperiod

import com.depoza.util.timeperiod.BasePeriod.WEEK_PERIOD
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test

class CurWeekPeriodTest {

    private val period = CurWeekPeriod()

    @Test
    fun shouldBeOfWeekType() {
        assertThat(period.type, `is`(WEEK_PERIOD))
    }

    @Test
    fun shouldBeOfCorrectQuantity() {
        assertThat(period.quantity, `is`(0))
    }

    @Test
    fun shouldBeStartedFromMonday() {
        assertThat(period.lowerBound, `is`(1))
    }
}