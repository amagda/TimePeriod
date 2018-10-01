package com.depoza.util.timeperiod

import com.depoza.util.timeperiod.BasePeriod.DAY_PERIOD
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test

class TodayPeriodTest {

    private val period = TodayPeriod()

    @Test
    fun shouldBeOfDayType() {
        assertThat(period.type, `is`(DAY_PERIOD))
    }

    @Test
    fun shouldBeOfCorrectQuantity() {
        assertThat(period.quantity, `is`(0))
    }
}