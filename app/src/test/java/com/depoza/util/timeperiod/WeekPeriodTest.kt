package com.depoza.util.timeperiod

import com.depoza.util.timeperiod.BasePeriod.WEEK_PERIOD
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.IntStream

class WeekPeriodTest {

    private val curPeriod = WeekPeriod(1, 0)
    private val previousPeriod = WeekPeriod(1, -1)
    private val nextPeriod = WeekPeriod(1, 1)

    @Test
    fun shouldBeOfWeekType() {
        assertThat(curPeriod.type, `is`(WEEK_PERIOD))
    }

    @ParameterizedTest
    @MethodSource("lowerBoundValues")
    fun constructsWithAllowedLowerBounds(lowerBound: Int) {
        WeekPeriod(lowerBound, 0)
    }

    @Test
    fun throwsOnLowerBoundLessThanAllowable() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            WeekPeriod(-1, 0)
        }
    }

    @Test
    fun throwsOnLowerBoundGreaterThanAllowable() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            WeekPeriod(8, 0)
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
        fun lowerBoundValues() = IntStream.range(0, 8)
    }
}