package com.example.feature_schedule.schedule.model

import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable

class HourUI(
    var id: Int,
    var x: Float = 0f,
    var y: Float = 0f,
    var time: String = "00:00",
    var color: Int = Color.WHITE

) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readString() ?: "00:00",
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeFloat(x)
        parcel.writeFloat(y)
        parcel.writeString(time)
        parcel.writeInt(color)
    }

    override fun describeContents(): Int = 0

    override fun toString(): String {
        return "HourUI(id=$id, x=$x, y=$y, time='$time', color=$color)"
    }

    companion object CREATOR : Parcelable.Creator<HourUI> {
        override fun createFromParcel(parcel: Parcel): HourUI = HourUI(parcel)

        override fun newArray(size: Int): Array<HourUI?> = arrayOfNulls(size)
    }
}