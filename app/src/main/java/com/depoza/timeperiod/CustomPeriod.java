package com.depoza.timeperiod;

import net.jcip.annotations.Immutable;

import java.util.Calendar;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.DAY_OF_YEAR;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MILLISECOND;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.SECOND;

/**
 * Not repeatable period of the time with specific date boundaries (start/end time)
 */
@Immutable
public final class CustomPeriod extends BasePeriod {

    /**
     * Period without specific start and end times
     */
    public static final CustomPeriod UNBOUNDED = new CustomPeriod(0, 0);

    /**
     * Lower boundary of the period.
     * <p>
     * Start date of the period in milliseconds Unix time format.
     * Could be {@code 0} if period is unbounded by lower boundary
     * </p>
     */
    public final long lowerBound;

    /**
     * Upper boundary of the period.
     * <p>
     * End date of the period in milliseconds Unix time format.
     * Could be {@code 0} if period is unbounded by upper boundary
     * </p>
     */
    public final long upperBound;

    /**
     * Initializes custom period
     *
     * @param lowerBound start date of the period in milliseconds Unix time format.
     *                   Could be {@code 0} if period is unbounded by lower boundary
     * @param upperBound end date of the period in milliseconds Unix time format.
     *                   Could be {@code 0} if period is unbounded by upper boundary
     */
    public CustomPeriod(long lowerBound, long upperBound) {
        super(CUSTOM_PERIOD, new CustomTimeBoundsCalculator());
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        boolean hasBoundaries = lowerBound > 0 && upperBound > 0;
        if (hasBoundaries && lowerBound > upperBound) {
            throw new IllegalArgumentException("lowerBound bigger than the upperBound: " +
                    "Should be less or equal");
        }
        if (hasBoundaries && upperBound < lowerBound) {
            throw new IllegalArgumentException("upperBound less than the lowerBound: " +
                    "Should be bigger or equal");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomPeriod)) return false;
        if (!super.equals(o)) return false;

        CustomPeriod that = (CustomPeriod) o;

        return lowerBound == that.lowerBound
                && upperBound == that.upperBound;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (lowerBound ^ (lowerBound >>> 32));
        result = 31 * result + (int) (upperBound ^ (upperBound >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "CustomPeriod{" +
                "lowerBound=" + lowerBound +
                ", upperBound=" + upperBound +
                "}";
    }

    @Override
    public CustomPeriod previous() {
        int delta = calculateDelta();
        return newPeriod(-delta);
    }

    @Override
    public CustomPeriod next() {
        int delta = calculateDelta();
        return newPeriod(delta);
    }

    private int calculateDelta() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(lowerBound);
        int startDayOfYear = calendar.get(DAY_OF_YEAR);

        calendar.setTimeInMillis(upperBound);
        int endDayOfYear = calendar.get(DAY_OF_YEAR);

        return endDayOfYear - startDayOfYear + 1;
    }

    private CustomPeriod newPeriod(int delta) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(lowerBound);
        calendar.add(DAY_OF_MONTH, delta);
        long newLowerBound = calendar.getTimeInMillis();

        calendar.setTimeInMillis(upperBound);
        calendar.add(DAY_OF_MONTH, delta);
        long newHigherBound = calendar.getTimeInMillis();

        return new CustomPeriod(newLowerBound, newHigherBound);
    }

    /**
     * Calculator of the {@link CustomPeriod} start/end time.
     * <p>
     * <ul>
     * <li>Sets start time (hh:mm:s) to 00:00:00</li>
     * <li>Sets end time (hh:mm:s) to 23:59:59</li>
     * </ul>
     * </p>
     */
    static final class CustomTimeBoundsCalculator implements TimeBoundsCalculator<CustomPeriod> {

        @Override
        public long[] calculateFor(CustomPeriod period) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(period.lowerBound);
            calendar.set(HOUR_OF_DAY, 0);
            calendar.set(MINUTE, 0);
            calendar.set(SECOND, 0);
            calendar.set(MILLISECOND, 0);
            long startTime = calendar.getTimeInMillis();

            calendar.setTimeInMillis(period.upperBound);
            calendar.set(HOUR_OF_DAY, 0);
            calendar.set(MINUTE, 0);
            calendar.set(SECOND, 0);
            calendar.set(MILLISECOND, 0);
            calendar.add(DAY_OF_MONTH, 1);
            calendar.add(MILLISECOND, -1);
            long endTime = calendar.getTimeInMillis();

            return new long[]{startTime, endTime};
        }
    }
}
