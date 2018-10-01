package com.depoza.util.timeperiod

import com.depoza.util.timeperiod.BasePeriod.MONTH_PERIOD
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test

class CurMonthPeriodTest {

    private val period = CurMonthPeriod()

    @Test
    fun shouldBeOfMonthType() {
        assertThat(period.type, `is`(MONTH_PERIOD))
    }

    @Test
    fun shouldBeOfCorrectQuantity() {
        assertThat(period.quantity, `is`(0))
    }

    @Test
    fun shouldBeStartedFrom1Day() {
        assertThat(period.lowerBound, `is`(1))
    }
}