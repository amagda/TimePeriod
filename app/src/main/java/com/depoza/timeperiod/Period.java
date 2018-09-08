package com.depoza.timeperiod;

import java.io.Serializable;

/**
 * Period of the time
 */
public interface Period extends Serializable {

    /**
     * Obtains the previous period in relation to this
     *
     * @return new {@code Period} instance
     */
    Period previous();

    /**
     * Obtains the next period in relation to this
     *
     * @return new {@code Period} instance
     */
    Period next();

    /**
     * Obtains start and end time of this period.
     * If period is {@link RepeatablePeriod}, then calculation is performed relative to the current time
     *
     * @return array with specific dates in Unix time format, where 1-t value is start time and 2-d is end time
     */
    long[] timeBounds();

    /**
     * Calculator of the {@link Period} start/end time
     *
     * @param <T> Period
     */
    @FunctionalInterface
    interface TimeBoundsCalculator<T extends Period> {
        /**
         * Obtains start and end time of period
         *
         * @param period to calculate start and end time for
         * @return array with specific dates in Unix time format, where 1-t value is start time and 2-d is end time
         */
        long[] calculateFor(T period);
    }
}
