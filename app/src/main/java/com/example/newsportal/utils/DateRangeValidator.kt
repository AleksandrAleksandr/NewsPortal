package com.example.newsportal.utils

import android.os.Parcel
import android.os.Parcelable
import com.google.android.material.datepicker.CalendarConstraints

class RangeValidator(private val minDate: Long, private val maxDate: Long) : CalendarConstraints.DateValidator{

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readLong()
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {}

    override fun describeContents() = 0

    override fun isValid(date: Long) = !(minDate > date || maxDate < date)

    companion object CREATOR : Parcelable.Creator<RangeValidator> {
        override fun createFromParcel(parcel: Parcel): RangeValidator {
            return RangeValidator(parcel)
        }

        override fun newArray(size: Int): Array<RangeValidator?> {
            return arrayOfNulls(size)
        }
    }
}