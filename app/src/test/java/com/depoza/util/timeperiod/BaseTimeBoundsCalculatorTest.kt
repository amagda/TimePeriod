package com.depoza.util.timeperiod

import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.util.*

abstract class BaseTimeBoundsCalculatorTest {

    protected val timeProvider: TimeProvider = mock(TimeProvider::class.java)

    protected fun provideTimeOf(calendar: Calendar) {
        `when`(timeProvider.currentTimeMls())
                .thenReturn(calendar.timeInMillis)
    }
}