package com.depoza.timeperiod;

import net.jcip.annotations.Immutable;

/**
 * Period of the time describing interval relative to the current time.
 * Repeatable means that start and end time would be recalculated on {@link #timeBounds()} method call
 * when current time cross the upper bound of this period.
 * <p>For example:
 * <ul>
 * <li>We defined period as current week started from monday with {@code new WeekPeriod(1,0)}</li>
 * <li>Today is sunday 20may</li>
 * <li>Then lower bound is 14may and upper is 20may</li>
 * <li>When it's monday of 21may(cross the upper bound of 20may) then start and end time would be recalculated,
 * so new current week lower bound is 21may and upper is 27may</li>
 * </ul>
 * </p>
 */
@Immutable
public abstract class RepeatablePeriod extends BasePeriod {

    /**
     * Lower boundary of the period.
     * <p>
     * Value depends of the {@link PeriodType}:
     * <ul>
     * <li>For the {@link #DAY_PERIOD} is current day(1)</li>
     * <li>For the {@link #WEEK_PERIOD} is day of the week in range of 1-7
     * or {@code 0} if period starts from the current day</li>
     * <li>For the {@link #MONTH_PERIOD} is day of the month in range of 1-31
     * or {@code 0} if period starts from the current day</li>
     * <li>For the {@link #YEAR_PERIOD} is month of the year in range of 1-12</li>
     * </ul>
     * </p>
     */
    public final int lowerBound;

    /**
     * Total number of the period units.
     * <p>
     * Supported range of -N to N:
     * <ul>
     * <li>{@code 0} value means the current period unit.
     * For example, current day/week/month/year</li>
     * <li>Negative value means the previous period unit in relation to the current.
     * For example value {@code -3} means previous 3-d day/week/month/year</li>
     * <li>Positive value means the next period unit in relation to the current.
     * For example value {@code 3} means next 3-d day/week/month/year</li>
     * </ul>
     * </p>
     */
    public final int quantity;

    RepeatablePeriod(@PeriodType String type, int lowerBound, int quantity,
                     TimeBoundsCalculator timeBoundsCalculator) {
        super(type, timeBoundsCalculator);
        this.lowerBound = lowerBound;
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RepeatablePeriod)) return false;
        if (!super.equals(o)) return false;

        RepeatablePeriod that = (RepeatablePeriod) o;

        return lowerBound == that.lowerBound
                && quantity == that.quantity;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + lowerBound;
        result = 31 * result + quantity;
        return result;
    }

    @Override
    public String toString() {
        return "RepeatablePeriod{" +
                "type='" + type + '\'' +
                ", lowerBound=" + lowerBound +
                ", quantity=" + quantity +
                "}";
    }
}
