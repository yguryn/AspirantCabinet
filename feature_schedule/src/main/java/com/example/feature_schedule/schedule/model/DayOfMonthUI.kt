package com.example.feature_schedule.schedule.model

import android.os.Parcel
import android.os.Parcelable

data class DayOfMonthUI(
    val id: Int,
    var day: Int,
    val nameOfDay: String,
    var isPressed: Boolean = false,
    val currentDay: Boolean = false,
    var currentMonth: Boolean = true,
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(day)
        parcel.writeString(nameOfDay)
        parcel.writeByte(if (isPressed) 1 else 0)
        parcel.writeByte(if (currentDay) 1 else 0)
        parcel.writeByte(if (currentMonth) 1 else 0)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<DayOfMonthUI> {
        override fun createFromParcel(parcel: Parcel): DayOfMonthUI = DayOfMonthUI(parcel)

        override fun newArray(size: Int): Array<DayOfMonthUI?> = arrayOfNulls(size)
    }
}