package com.depoza.util.timeperiod

import java.io.Serializable

/**
 * Period of the time
 */
interface Period : Serializable {

    /**
     * Obtains the previous period in relation to this
     *
     * @return new [Period] instance
     */
    fun previous(): Period

    /**
     * Obtains the next period in relation to this
     *
     * @return new [Period] instance
     */
    fun next(): Period

    /**
     * Obtains start and end time of this period.
     * If period is [RepeatablePeriod], then calculation is performed relative to the current time
     *
     * @return array with specific dates in Unix time format, where 1-t value is start time and 2-d is end time
     */
    fun timeBounds(): LongArray

    /**
     * Calculator of the [Period] start/end time
     *
     * @param <T> Period
     */
    @FunctionalInterface
    interface TimeBoundsCalculator<T : Period> {
        /**
         * Obtains start and end time of period
         *
         * @param period to calculate start and end time for
         * @return array with specific dates in Unix time format, where 1-t value is start time and 2-d is end time
         */
        fun calculateFor(period: T): LongArray
    }
}
