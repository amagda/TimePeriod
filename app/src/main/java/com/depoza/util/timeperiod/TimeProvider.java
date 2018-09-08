package com.depoza.util.timeperiod;

/**
 * Provider of the current time in milliseconds
 */
@FunctionalInterface
public interface TimeProvider {
    long currentTimeMls();

    /**
     * {@link TimeProvider} which uses {@link System#currentTimeMillis()}
     */
    class DefaultTimeProvider implements TimeProvider {

        @Override
        public long currentTimeMls() {
            return System.currentTimeMillis();
        }
    }
}
