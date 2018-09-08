package com.depoza.util.timeperiod;

import android.support.annotation.StringDef;

import net.jcip.annotations.Immutable;

import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;

/**
 * Period of the time with a common information
 */
@Immutable
public abstract class BasePeriod implements Period {

    public static final String CUSTOM_PERIOD = "custom";
    public static final String DAY_PERIOD = "day";
    public static final String WEEK_PERIOD = "week";
    public static final String MONTH_PERIOD = "month";
    public static final String YEAR_PERIOD = "year";

    /**
     * Type of the period
     *
     * @see PeriodType
     */
    @PeriodType
    public final String type;

    private final transient TimeBoundsCalculator timeBoundsCalculator;

    protected BasePeriod(@PeriodType String type, TimeBoundsCalculator timeBoundsCalculator) {
        if (type == null || type.trim().isEmpty()) {
            throw new NullPointerException();
        }
        if (timeBoundsCalculator == null) {
            throw new NullPointerException();
        }
        this.type = type;
        this.timeBoundsCalculator = timeBoundsCalculator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BasePeriod that = (BasePeriod) o;

        return type != null ? type.equals(that.type) : that.type == null;
    }

    @Override
    public int hashCode() {
        return type != null ? type.hashCode() : 0;
    }

    @Override
    public final long[] timeBounds() {
        return timeBoundsCalculator.calculateFor(this);
    }

    /**
     * Denotes that the annotated {@code String} element represents a type of the date period
     * and that its value should be one of the explicitly named constants
     */
    @Target({FIELD, PARAMETER})
    @StringDef({CUSTOM_PERIOD, DAY_PERIOD, WEEK_PERIOD, MONTH_PERIOD, YEAR_PERIOD})
    public @interface PeriodType {
    }
}
