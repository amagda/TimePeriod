package com.depoza.util.timeperiod

import com.depoza.util.timeperiod.BasePeriod.YEAR_PERIOD
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test

class PrevYearPeriodTest {

    private val period = PrevYearPeriod()

    @Test
    fun shouldBeOfYearType() {
        assertThat(period.type, `is`(YEAR_PERIOD))
    }

    @Test
    fun shouldBeOfCorrectQuantity() {
        assertThat(period.quantity, `is`(-1))
    }

    @Test
    fun shouldBeStartedFromJanuary() {
        assertThat(period.lowerBound, `is`(1))
    }
}