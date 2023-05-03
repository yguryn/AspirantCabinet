package com.example.feature_schedule.schedule.model

import android.os.Parcel
import android.os.Parcelable
import java.util.Calendar

class SelectedDate(
    var currentDay: Int,
    var currentMonth: Int,
    var currentYear: Int,
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    )

    fun getCalendar(): Calendar {
        return Calendar.getInstance().apply {
            set(Calendar.DAY_OF_MONTH, this@SelectedDate.currentDay)
            set(Calendar.MONTH, this@SelectedDate.currentMonth)
            set(Calendar.YEAR, this@SelectedDate.currentYear)
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(currentDay)
        parcel.writeInt(currentMonth)
        parcel.writeInt(currentYear)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SelectedDate> {
        override fun createFromParcel(parcel: Parcel): SelectedDate {
            return SelectedDate(parcel)
        }

        override fun newArray(size: Int): Array<SelectedDate?> {
            return arrayOfNulls(size)
        }
    }
}
