package com.depoza.timeperiod

import com.depoza.timeperiod.BasePeriod.MONTH_PERIOD
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.IntStream

class MonthPeriodTest {

    private val curPeriod = MonthPeriod(1, 0)
    private val previousPeriod = MonthPeriod(1, -1)
    private val nextPeriod = MonthPeriod(1, 1)

    @Test
    fun shouldBeOfMonthType() {
        assertThat(curPeriod.type, `is`(MONTH_PERIOD))
    }

    @ParameterizedTest
    @MethodSource("lowerBoundValues")
    fun constructsWithAllowedLowerBounds(lowerBound: Int) {
        MonthPeriod(lowerBound, 0)
    }

    @Test
    fun throwsOnLowerBoundLessThanAllowable() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            MonthPeriod(-1, 0)
        }
    }

    @Test
    fun throwsOnLowerBoundGreaterThanAllowable() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            MonthPeriod(32, 0)
        }
    }

    @Test
    fun previousReturnsNewInstance() {
        assertEquals(previousPeriod, curPeriod.previous())
    }

    @Test
    fun nextReturnsNewInstance() {
        assertEquals(nextPeriod, curPeriod.next())
    }

    companion object {
        @JvmStatic
        fun lowerBoundValues() = IntStream.range(0, 32)
    }
}