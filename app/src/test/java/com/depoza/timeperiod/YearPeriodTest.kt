package com.depoza.timeperiod

import com.depoza.timeperiod.BasePeriod.YEAR_PERIOD
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.IntStream

class YearPeriodTest {

    private val curPeriod = YearPeriod(1, 0)
    private val previousPeriod = YearPeriod(1, -1)
    private val nextPeriod = YearPeriod(1, 1)

    @Test
    fun shouldBeOfMonthType() {
        assertThat(curPeriod.type, `is`(YEAR_PERIOD))
    }

    @ParameterizedTest
    @MethodSource("lowerBoundValues")
    fun constructsWithAllowedLowerBounds(lowerBound: Int) {
        YearPeriod(lowerBound, 0)
    }

    @Test
    fun throwsOnLowerBoundLessThanAllowable() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            YearPeriod(0, 0)
        }
    }

    @Test
    fun throwsOnLowerBoundGreaterThanAllowable() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            YearPeriod(13, 0)
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
        fun lowerBoundValues() = IntStream.range(1, 13)
    }
}