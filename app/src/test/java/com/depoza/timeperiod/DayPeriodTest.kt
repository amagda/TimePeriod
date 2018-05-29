package com.depoza.timeperiod

import com.depoza.timeperiod.BasePeriod.DAY_PERIOD
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DayPeriodTest {

    private val curPeriod = DayPeriod(0)
    private val previousPeriod = DayPeriod(-1)
    private val nextPeriod = DayPeriod(1)

    @Test
    fun shouldBeOfDayType() {
        assertThat(curPeriod.type, `is`(DAY_PERIOD))
    }

    @Test
    fun previousReturnsNewInstance() {
        assertEquals(previousPeriod, curPeriod.previous())
    }

    @Test
    fun nextReturnsNewInstance() {
        assertEquals(nextPeriod, curPeriod.next())
    }
}