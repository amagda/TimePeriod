package com.depoza.util.timeperiod

import com.depoza.util.timeperiod.BasePeriod.CUSTOM_PERIOD
import com.depoza.util.timeperiod.CustomPeriod.UNBOUNDED
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import java.util.*
import java.util.Calendar.*

@TestInstance(PER_CLASS)
class CustomPeriodTest {

    private val cal5Apr2018 = Calendar.getInstance().apply {
        set(DAY_OF_MONTH, 5)
        set(MONTH, 3)
        set(YEAR, 2018)
        set(HOUR_OF_DAY, 13)
        set(MINUTE, 25)
        set(SECOND, 50)
        set(MILLISECOND, 55)
    }
    private val cal11Apr2018 = Calendar.getInstance().apply {
        set(DAY_OF_MONTH, 11)
        set(MONTH, 3)
        set(YEAR, 2018)
        set(HOUR_OF_DAY, 20)
        set(MINUTE, 20)
        set(SECOND, 20)
        set(MILLISECOND, 20)
    }

    @Test
    fun shouldBeOfCustomType() {
        assertThat(UNBOUNDED.type, `is`(CUSTOM_PERIOD))
    }

    @Test
    fun unboundedPeriodBoundariesShouldBeZero() {
        assertThat(UNBOUNDED.lowerBound, `is`(0L))
        assertThat(UNBOUNDED.upperBound, `is`(0L))
    }

    @Test
    fun constructsUnboundedByLowerBoundary() {
        CustomPeriod(0, cal11Apr2018.timeInMillis)
    }

    @Test
    fun constructsUnboundedByHigherBoundary() {
        CustomPeriod(cal5Apr2018.timeInMillis, 0)
    }

    @Test
    fun throwsOnLowerBoundGreaterThanHigher() {
        assertThrows(IllegalArgumentException::class.java) {
            CustomPeriod(cal11Apr2018.timeInMillis, cal5Apr2018.timeInMillis)
        }
    }

    @Test
    fun throwsOnHigherBoundLessThanLower() {
        assertThrows(IllegalArgumentException::class.java) {
            CustomPeriod(cal11Apr2018.timeInMillis, cal5Apr2018.timeInMillis)
        }
    }

    @Test
    fun previousReturnsNewInstance() {
        val actual = CustomPeriod(cal5Apr2018.timeInMillis, cal11Apr2018.timeInMillis).previous()
        val timeBounds = longArrayOf(actual.lowerBound, actual.upperBound)
        PeriodTimeBoundsVerifier(timeBounds)
                .lowerBound(29, 3, 2018, 13, 25, 50, 55)
                .upperBound(4, 4, 2018, 20, 20, 20, 20)
                .verify()
    }

    @Test
    fun nextReturnsNewInstance() {
        val actual = CustomPeriod(cal5Apr2018.timeInMillis, cal11Apr2018.timeInMillis).next()
        val timeBounds = longArrayOf(actual.lowerBound, actual.upperBound)
        PeriodTimeBoundsVerifier(timeBounds)
                .lowerBound(12, 4, 2018, 13, 25, 50, 55)
                .upperBound(18, 4, 2018, 20, 20, 20, 20)
                .verify()
    }
}