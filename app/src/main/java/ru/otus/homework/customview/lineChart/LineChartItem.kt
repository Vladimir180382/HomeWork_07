package ru.otus.homework.customview.lineChart

import android.graphics.PointF
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LineChartItem(
    val category: String,
    val amount: Int,
    val time: Long
) : Parcelable

@Parcelize
data class LineChartRange(
    val points: List<PointF>,
    val color: Int
) : Parcelable