package pl.matyjasiakm.volunteermanager.utils

import android.text.Editable
import java.util.*

fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

fun Calendar.toStringDate() = "${
    this.get(Calendar.DAY_OF_MONTH).toString().padStart(2, '0')
}.${(this.get(Calendar.MONTH) + 1).toString().padStart(2, '0')}.${this.get(Calendar.YEAR)}"

fun Calendar.toStringTime() = "${
    this.get(Calendar.HOUR_OF_DAY).toString().padStart(2, '0')
}:${this.get(Calendar.MINUTE).toString().padStart(2, '0')}"

fun toStringDate(dayOfMonth: Int, month: Int, year: Int): String{
    return "${dayOfMonth.toString().padStart(2, '0')}.${
        (month + 1).toString().padStart(2, '0')
    }.$year"
}

fun toStringTime(hourOfDay:Int,minute:Int):String{
    return "${hourOfDay.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}"
}