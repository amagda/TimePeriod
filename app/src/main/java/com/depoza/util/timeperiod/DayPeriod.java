package com.depoza.util.timeperiod;

import android.support.annotation.VisibleForTesting;

import net.jcip.annotations.Immutable;

import java.util.Calendar;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MILLISECOND;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.SECOND;

/**
 * Period of the time repeating every day relative to the current time
 */
@Immutable
public final class DayPeriod extends RepeatablePeriod {

    /**
     * Initializes day period
     *
     * @param quantity total number of the days in relation to the current time
     *                 where {@code 0} is current day,
     *                 negative value is previous N days,
     *                 positive value is next N days
     */
    public DayPeriod(int quantity) {
        super(DAY_PERIOD, 1, quantity, new DayTimeBoundsCalculator());
    }

    @Override
    public BasePeriod previous() {
        return new DayPeriod(quantity - 1);
    }

    @Override
    public BasePeriod next() {
        return new DayPeriod(quantity + 1);
    }

    /**
     * Calculator of the {@link DayPeriod} start/end time.
     * Calculation performs relative to the current time.
     * <p>
     * <ul>
     * <li>Sets start time (hh:mm:s) to 00:00:00</li>
     * <li>Sets end time (hh:mm:s) to 23:59:59</li>
     * </ul>
     * </p>
     */
    static final class DayTimeBoundsCalculator implements TimeBoundsCalculator<DayPeriod> {

        private final TimeProvider timeProvider;

        DayTimeBoundsCalculator() {
            timeProvider = new TimeProvider.DefaultTimeProvider();
        }

        /**
         * Useful from the tests to provide mock implementation of the {@link TimeProvider}
         */
        @VisibleForTesting()
        DayTimeBoundsCalculator(TimeProvider timeProvider) {
            this.timeProvider = timeProvider;
        }

        @Override
        public long[] calculateFor(DayPeriod period) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(timeProvider.currentTimeMls());

            calendar.set(HOUR_OF_DAY, 0);
            calendar.set(MINUTE, 0);
            calendar.set(SECOND, 0);
            calendar.set(MILLISECOND, 0);
            calendar.add(DAY_OF_MONTH, period.quantity);
            long startTime = calendar.getTimeInMillis();

            calendar.add(DAY_OF_MONTH, 1);
            calendar.add(MILLISECOND, -1);
            long endTime = calendar.getTimeInMillis();

            return new long[]{startTime, endTime};
        }
    }
}
