package ru.otus.homework.customview.pieChart

import android.os.Parcel
import android.os.Parcelable
import android.view.View

internal class PieChartSavedState : View.BaseSavedState {
    var centerText: String? = null
    var sectionList: List<PieChartSection> = emptyList()

    constructor(superState: Parcelable?) : super(superState)

    private constructor(source: Parcel) : super(source) {
        centerText = source.readString()

        source.readList(sectionList, ClassLoader.getSystemClassLoader())
    }

    companion object CREATOR : Parcelable.Creator<PieChartSavedState> {
        override fun createFromParcel(parcel: Parcel): PieChartSavedState {
            return PieChartSavedState(parcel)
        }

        override fun newArray(size: Int): Array<PieChartSavedState?> {
            return arrayOfNulls(size)
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        super.writeToParcel(parcel, flags)
        parcel.writeString(centerText)
        parcel.writeTypedList(sectionList)
    }

    override fun describeContents(): Int {
        return 0
    }
}
