package com.depoza.timeperiod;

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

/**
 * Period of the time repeating every month relative to the current time.
 * Could be started from any day of the month
 */
@Immutable
public final class MonthPeriod extends RepeatablePeriod {

    /**
     * Initializes month period
     *
     * @param lowerBound day of the month in range of 1-31 from which period could be started
     *                   or {@code 0} if period starts from the current day
     * @param quantity   total number of the months in relation to the current time
     *                   where {@code 0} is current month,
     *                   negative value is previous N months,
     *                   positive value is next N months
     */
    public MonthPeriod(@IntRange(from = 0, to = 31) int lowerBound, int quantity) {
        super(MONTH_PERIOD, lowerBound, quantity, new MonthTimeBoundsCalculator());
        if (lowerBound < 0 || lowerBound > 31) {
            throw new IllegalArgumentException("lowerBound is day of the month, value should be in range of 1-31" +
                    " or 0 if period starts from the current day");
        }
    }

    @Override
    public BasePeriod previous() {
        return new MonthPeriod(lowerBound, quantity - 1);
    }

    @Override
    public BasePeriod next() {
        return new MonthPeriod(lowerBound, quantity + 1);
    }

    /**
     * Calculator of the {@link MonthPeriod} start/end time.
     * Calculation performs relative to the current time.
     * <p>
     * <ul>
     * <li>Sets start time (hh:mm:s) to 00:00:00</li>
     * <li>Sets end time (hh:mm:s) to 23:59:59</li>
     * </ul>
     * </p>
     */
    static final class MonthTimeBoundsCalculator implements TimeBoundsCalculator<MonthPeriod> {

        private final TimeProvider timeProvider;

        MonthTimeBoundsCalculator() {
            timeProvider = new TimeProvider.DefaultTimeProvider();
        }

        /**
         * Useful from the tests to provide mock implementation of the {@link TimeProvider}
         */
        @VisibleForTesting()
        MonthTimeBoundsCalculator(TimeProvider timeProvider) {
            this.timeProvider = timeProvider;
        }

        @Override
        public long[] calculateFor(MonthPeriod period) {
            boolean isLast30DaysMode = period.lowerBound == 0;
            if (isLast30DaysMode) {
                return calculateLast30DaysFor(period);
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(timeProvider.currentTimeMls());

            int curDay = calendar.get(DAY_OF_MONTH);
            int lowerDay = period.lowerBound;

            calendar.set(DAY_OF_MONTH, 1); //to avoid handling max days for different months
            calendar.add(MONTH, period.quantity);

            if (lowerDay > curDay) {
                calendar.add(MONTH, -1);
            }
            int maxDay = calendar.getActualMaximum(DAY_OF_MONTH);
            if (lowerDay > maxDay) {
                lowerDay = maxDay;
            }
            calendar.set(DAY_OF_MONTH, lowerDay);
            calendar.set(HOUR_OF_DAY, 0);
            calendar.set(MINUTE, 0);
            calendar.set(SECOND, 0);
            calendar.set(MILLISECOND, 0);
            long startTime = calendar.getTimeInMillis();

            calendar.set(DAY_OF_MONTH, 1); //to avoid handling max day for different months
            calendar.add(MONTH, 1);
            maxDay = calendar.getActualMaximum(DAY_OF_MONTH);
            if (lowerDay > maxDay) {
                lowerDay = maxDay;
                calendar.set(DAY_OF_MONTH, lowerDay);
                calendar.add(DAY_OF_MONTH, 1);
            } else {
                calendar.set(DAY_OF_MONTH, lowerDay);
            }
            calendar.add(MILLISECOND, -1);
            long endTime = calendar.getTimeInMillis();

            return new long[]{startTime, endTime};
        }

        private long[] calculateLast30DaysFor(MonthPeriod period) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(timeProvider.currentTimeMls());

            int curDay = calendar.get(Calendar.DAY_OF_YEAR);
            int lowerDay = curDay + (30 * period.quantity);

            calendar.set(Calendar.DAY_OF_YEAR, lowerDay);
            calendar.set(HOUR_OF_DAY, 0);
            calendar.set(MINUTE, 0);
            calendar.set(SECOND, 0);
            calendar.set(MILLISECOND, 0);
            long startTime = calendar.getTimeInMillis();

            calendar.add(Calendar.DAY_OF_YEAR, 30);
            calendar.add(MILLISECOND, -1);
            long endTime = calendar.getTimeInMillis();

            return new long[]{startTime, endTime};
        }
    }
}
