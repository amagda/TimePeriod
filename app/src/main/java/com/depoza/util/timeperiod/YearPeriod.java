package com.depoza.util.timeperiod;

import android.support.annotation.IntRange;
import android.support.annotation.VisibleForTesting;

import net.jcip.annotations.Immutable;

import java.util.Calendar;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MILLISECOND;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.SECOND;
import static java.util.Calendar.YEAR;

/**
 * Period of the time repeating every year relative to the current time.
 * Could be started from any month of the year
 */
@Immutable
public final class YearPeriod extends RepeatablePeriod {

    /**
     * Initializes year period
     *
     * @param lowerBound month of the year in range of 1-12(January-December) from which period could be started
     * @param quantity   total number of the years in relation to the current time
     *                   where {@code 0} is current year,
     *                   negative value is previous N years,
     *                   positive value is next N years
     */
    public YearPeriod(@IntRange(from = 1, to = 12) int lowerBound, int quantity) {
        super(YEAR_PERIOD, lowerBound, quantity, new YearTimeBoundsCalculator());
        if (lowerBound < 1 || lowerBound > 12) {
            throw new IllegalArgumentException("lowerBound is month of the year, value should be in range of 1-12");
        }
    }

    @Override
    public BasePeriod previous() {
        return new YearPeriod(lowerBound, quantity - 1);
    }

    @Override
    public BasePeriod next() {
        return new YearPeriod(lowerBound, quantity + 1);
    }

    /**
     * Calculator of the {@link YearPeriod} start/end time.
     * Calculation performs relative to the current time.
     * <p>
     * <ul>
     * <li>Sets start time (hh:mm:s) to 00:00:00</li>
     * <li>Sets end time (hh:mm:s) to 23:59:59</li>
     * </ul>
     * </p>
     */
    static final class YearTimeBoundsCalculator implements TimeBoundsCalculator<YearPeriod> {

        private final TimeProvider timeProvider;

        YearTimeBoundsCalculator() {
            timeProvider = new TimeProvider.DefaultTimeProvider();
        }

        /**
         * Useful from the tests to provide mock implementation of the {@link TimeProvider}
         */
        @VisibleForTesting()
        YearTimeBoundsCalculator(TimeProvider timeProvider) {
            this.timeProvider = timeProvider;
        }

        @Override
        public long[] calculateFor(YearPeriod period) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(timeProvider.currentTimeMls());

            int curMonth = convertRawMonthToLocal(calendar.get(MONTH));
            int lowerMonth = period.lowerBound;

            calendar.set(DAY_OF_MONTH, 1);
            calendar.set(HOUR_OF_DAY, 0);
            calendar.set(MINUTE, 0);
            calendar.set(SECOND, 0);
            calendar.set(MILLISECOND, 0);
            calendar.set(MONTH, lowerMonth - 1);
            if (lowerMonth > curMonth) {
                calendar.add(YEAR, -1);
            }
            calendar.add(YEAR, period.quantity);
            long startTime = calendar.getTimeInMillis();

            calendar.add(YEAR, 1);
            calendar.add(MILLISECOND, -1);
            long endTime = calendar.getTimeInMillis();

            return new long[]{startTime, endTime};
        }

        private int convertRawMonthToLocal(int rawMonth) {
            // calendar.get(MONTH) returns int value of corresponding month with delta '-1'.
            // For example, Jan-0, Feb-1, March-3 and etc.
            // So we need to convert raw values to supported by YearPeriod,
            // where month in range of 1-12(January-December)
            return rawMonth + 1;
        }
    }
}
