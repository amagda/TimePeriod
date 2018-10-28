# TimePeriod
[ ![Download](https://api.bintray.com/packages/depoza/util/time-period/images/download.svg) ](https://bintray.com/depoza/util/time-period/_latestVersion)
 
Set of simple classes for working with time periods.

Useful while implementing features with configuring filters or reports for different time periods. For example, transactions filtering of finance trackers or configuring analytics reports.

## How to add
```java
dependencies {
    implementation 'com.depoza.util:time-period:X.Y.Z'
}
```

## Period
Allowes you to obtain: 
- `long[] timeBounds()` start and end times of this period
- `Period previous()` previous period in relation to this
- `Period next()` next period in relation to this

## CustomPeriod
Represents the specific lower and upper boundaries.
```kotlin
val lowerBound = System.currentTimeMillis()
val upperBound = lowerBound + DAY_IN_MLS
CustomPeriod(lowerBound, upperBound)
```
Or unbounded by lower/upper bounds
```kotlin
CustomPeriod(0, upperBound)
CustomPeriod(lowerBound, 0)
```
Or unbounded by lower and upper bounds at same time
```kotlin
CustomPeriod(0, 0)
CustomPeriod.UNBOUNDED
```

## RepeatablePeriod
Describes period relative to the current time.

Supported types:
- `DayPeriod`
- `WeekPeriod`
- `MonthPeriod`
- `YearPeriod`

Repeatable means that lower and upper bounds would be recalculated on `timeBounds()` method call when current time cross the upper bound of this period.

For example:
- We defined period as current week started from monday with `WeekPeriod(1,0)`
- Today is sunday 20may
- Then lower bound is 14may and upper is 20may
- When it's monday of 21may(cross the upper bound of 20may) then bounds would be recalculated, so new current week lower bound is 21may and upper is 27may
 
**DayPeriod**
###### Predefined classes
- `TodayPeriod`
- `YesterdayPeriod`

```kotlin
// current day
DayPeriod(0)

// yesterday
DayPeriod(-1)

// previous 3-d day
DayPeriod(-3)

// next day
DayPeriod(1)
```

**WeekPeriod**

Lower bound in range of 1-7(monday-sunday) or 0 if period starts from the current day

###### Predefined classes
- `CurWeekPeriod`
- `PrevWeekPeriod`
- `Last7DayPeriod`

```kotlin
// current week, lower bound is current day
WeekPeriod(0, 0)	

// current week, lower bound is monday
WeekPeriod(1, 0)

// previous week, lower bound is monday
WeekPeriod(1, -1)

// next 3-d week, lower bound is friday
WeekPeriod(5, 3)	
```

**MonthPeriod**

Lower bound in range of 1-31(day of month) or 0 if period starts from the current day

###### Predefined classes
- `CurMonthPeriod`
- `PrevMonthPeriod`
- `Last30DayPeriod`

```kotlin
// current month, lower bound is current day
MonthPeriod(0, 0)	

// current month, lower bound is 1 day of month
MonthPeriod(1, 0)	

// previous month, lower bound is 20 day of month
MonthPeriod(20, -1)	

// next month, lower bound is 31 day of month
MonthPeriod(31, 1)	
```

**YearPeriod**

Lower bound in range of 1-12(month of year)

###### Predefined classes
- `CurYearPeriod`
- `PrevYearPeriod`

```kotlin
// current year, lower bound is january
YearPeriod(1, 0) 			

// previous year, lower bound is june
YearPeriod(6, -1)		

// next year, lower bound is november
YearPeriod(11, 1)				
```
