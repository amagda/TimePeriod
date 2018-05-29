package com.depoza.timeperiod;

import android.support.annotation.IntRange;
import android.support.annotation.VisibleForTesting;

import net.jcip.annotations.Immutable;

import java.util.Calendar;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MILLISECOND;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.SECOND;
import static java.util.Calendar.SUNDAY;

/**
 * Period of the time repeating every week relative to the current time.
 * Could be started from any day of the week
 */
@Immutable
public final class WeekPeriod extends RepeatablePeriod {

    /**
     * @param lowerBound day of the week in range of 1-7(Monday-Sunday) from which period could be started
     * @param quantity   total number of the weeks in relation to the current time
     *                   where {@code 0} is current week,
     *                   negative value is previous N weeks,
     *                   positive value is next N weeks
     */
    public WeekPeriod(@IntRange(from = 1, to = 7) int lowerBound, int quantity) {
        super(WEEK_PERIOD, lowerBound, quantity, new WeekTimeBoundsCalculator());
        if (lowerBound < 1 || lowerBound > 7) {
            throw new IllegalArgumentException("lowerBound is day of the week, value should be in range of 1-7");
        }
    }

    @Override
    public BasePeriod previous() {
        return new WeekPeriod(lowerBound, quantity - 1);
    }

    @Override
    public BasePeriod next() {
        return new WeekPeriod(lowerBound, quantity + 1);
    }

    /**
     * Calculator of the {@link WeekPeriod} start/end time.
     * Calculation performs relative to the current time.
     * <p>
     * <ul>
     * <li>Sets start time (hh:mm:s) to 00:00:00</li>
     * <li>Sets end time (hh:mm:s) to 23:59:59</li>
     * </ul>
     * </p>
     */
    static final class WeekTimeBoundsCalculator implements TimeBoundsCalculator<WeekPeriod> {

        private final TimeProvider timeProvider;

        WeekTimeBoundsCalculator() {
            timeProvider = new TimeProvider.DefaultTimeProvider();
        }

        /**
         * Useful from the tests to provide mock implementation of the {@link TimeProvider}
         */
        @VisibleForTesting()
        WeekTimeBoundsCalculator(TimeProvider timeProvider) {
            this.timeProvider = timeProvider;
        }

        @Override
        public long[] calculateFor(WeekPeriod period) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(timeProvider.currentTimeMls());

            int curDay = convertRawDayOfWeekToLocal(calendar.get(DAY_OF_WEEK));
            int delta = calculateDelta(period.lowerBound, curDay);

            calendar.set(HOUR_OF_DAY, 0);
            calendar.set(MINUTE, 0);
            calendar.set(SECOND, 0);
            calendar.set(MILLISECOND, 0);
            calendar.add(DAY_OF_MONTH, -delta);
            calendar.add(DAY_OF_MONTH, period.quantity * 7);
            long startTime = calendar.getTimeInMillis();

            calendar.add(DAY_OF_MONTH, 7);
            calendar.add(MILLISECOND, -1);
            long endTime = calendar.getTimeInMillis();

            return new long[]{startTime, endTime};
        }

        private int calculateDelta(int lowerDayOfWeek, int curDayOfWeek) {
            int result = curDayOfWeek - lowerDayOfWeek;
            if (curDayOfWeek < lowerDayOfWeek) {
                result += 7;
            }
            return result;
        }

        private int convertRawDayOfWeekToLocal(int rawDayOfWeek) {
            // calendar.get(DAY_OF_WEEK) returns int value of corresponding constants Mon/Tue/Wed/Thu/Fr/Sat/Sunday
            // but Sunday declared with value 1 and other days with delta +1
            // Sunday-1,Mon-2,Tue-3,Wed-4,Thu-5,Fr-6,Sat-7
            // So we need to convert raw values to supported by WeekPeriod,
            // where day of the week in range of 1-7(Mon-Sunday)
            switch (rawDayOfWeek) {
                case SUNDAY:
                    return 7;
                default:
                    return rawDayOfWeek - 1;
            }
        }
    }
}
