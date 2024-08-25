package com.example.todolist

import android.os.Parcel
import android.os.Parcelable
import android.widget.EditText
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class Task(private var noum: String, private var description: String, private var date: LocalDate?, private var priority: Int) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString()?.let { LocalDate.parse(it) },
        parcel.readInt()
    )

    override fun writeToParcel(p0: Parcel, p1: Int) {
        p0.writeString(noum)
        p0.writeString(description)
        p0.writeString(date?.toString())
        p0.writeInt(priority)
    }


    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Task> {
        override fun createFromParcel(parcel: Parcel): Task {
            return Task(parcel)
        }

        override fun newArray(size: Int): Array<Task?> {
            return arrayOfNulls(size)
        }
    }

    fun getDateFormated() : String {
        return date?.format(DateTimeFormatter.ofPattern("dd/MM")) ?: ""
    }


    fun getNoum(): String {
        return noum
    }

    fun getDescription(): String {
        return description
    }

    fun getDate(): LocalDate? {
        return date
    }

    fun getPriority(): Int {
        return priority
    }

    private fun getFormatLocalDate(editText: EditText, dateFormat: String = "dd/MM/yyyy"): LocalDate? {
        val date: String = editText.text.toString()
        val formatter = DateTimeFormatter.ofPattern(dateFormat)

        return LocalDate.parse(date, formatter)
    }
}