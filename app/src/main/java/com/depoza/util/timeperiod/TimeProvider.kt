package com.depoza.util.timeperiod

/**
 * Provider of the current time in milliseconds
 */
@FunctionalInterface
interface TimeProvider {
    fun currentTimeMls(): Long

    /**
     * [TimeProvider] which uses [System.currentTimeMillis]
     */
    class DefaultTimeProvider : TimeProvider {

        override fun currentTimeMls(): Long {
            return System.currentTimeMillis()
        }
    }
}
