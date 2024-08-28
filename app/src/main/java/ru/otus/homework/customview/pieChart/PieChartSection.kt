package ru.otus.homework.customview.pieChart

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
data class PieChartSection(
    val pieChartItems: List<PieChartItem>,
    val startAngle: Float,
    val sweepAngle: Float,
    val color: Int
) : Parcelable

@Parcelize
data class PieChartItem(
    val name: String,
    val category: String,
    val amount: Int
) : Parcelable

@Serializable
data class PayloadData(
    val id: Int,
    val name: String,
    val amount: Int,
    val category: String,
    val time: Long
)