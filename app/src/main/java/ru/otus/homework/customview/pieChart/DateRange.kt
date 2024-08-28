package ru.otus.homework.customview.pieChart

import java.util.Calendar
import java.util.Date
import java.util.Locale

class DateRange(
    override val start: Date,
    override val endInclusive: Date,
    private val stepDays: Int = 1
) : Iterable<Date>, ClosedRange<Date> {

    override fun iterator(): Iterator<Date> = DateIterator(start, endInclusive, stepDays)
}

class DateIterator(
    startDate: Date,
    private val endDate: Date,
    private val stepDays: Int
) : Iterator<Date> {

    private val calendar = Calendar.getInstance(Locale.getDefault())

    private var currentDate = startDate

    override fun hasNext() = currentDate <= endDate

    override fun next(): Date {
        val next = currentDate

        calendar.time = currentDate
        calendar.add(Calendar.DATE, stepDays)
        currentDate = calendar.time

        return next
    }
}