package com.thousand.bus.global.extension

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

fun Int?.getKZTCurrencyFormat(): String{
    if (this == null) return ""
    val format = NumberFormat.getCurrencyInstance()
    format.maximumFractionDigits = 0
    format.currency = Currency.getInstance("KZT")
    return format.format(this)
}

fun String?.getFormattedDate(): String{
    if (this == null) return ""
    val fromDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val toDate = SimpleDateFormat("d MMMM, EEEE", Locale.getDefault())
    val date = fromDate.parse(this)
    return toDate.format(date)
}

fun String?.getFormattedDateV(): String{
    if (this == null) return ""
    val fromDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val toDate = SimpleDateFormat("d MMMM", Locale.getDefault())
    val date = fromDate.parse(this)
    return toDate.format(date)
}

fun String?.getFormattedDateZ(): String{
    if (this == null) return ""
    val fromDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val toDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val date = fromDate.parse(this)
    return toDate.format(date)
}

fun String?.getFormattedDateAndTimeT(): String{
    if (this == null) return ""
    val fromDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX", Locale.getDefault())
    val toDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val date = fromDate.parse(this)
    return toDate.format(date)
}

fun String?.getFormattedDateT(): String{
    if (this == null) return ""
    val fromDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX", Locale.getDefault())
    val toDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val date = fromDate.parse(this)
    return toDate.format(date)
}

fun String?.getFormattedTimeT(): String{
    if (this == null) return ""
    val fromDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX", Locale.getDefault())
    val toDate = SimpleDateFormat("mm:ss", Locale.getDefault())
    val date = fromDate.parse(this)
    return toDate.format(date)
}

fun String?.getFormattedTime(): String{
    if (this == null) return ""
    val fromDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val toDate = SimpleDateFormat("HH:mm", Locale.getDefault())
    val date = fromDate.parse(this)
    return toDate.format(date)
}

fun String?.getFormattedDateAndTime(): String{
    if (this == null) return ""
    val fromDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val toDate = SimpleDateFormat("d MMMM / HH:mm", Locale.getDefault())
    val date = fromDate.parse(this)
    return toDate.format(date)
}

fun String?.getFormattedDateAndTime_(): String{
    if (this == null) return ""
    val fromDate = SimpleDateFormat("yyyy-MM-dd HH-mm-ss", Locale.getDefault())
    val toDate = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
    val date = fromDate.parse(this)
    return toDate.format(date)
}

fun String?.getFormattedDateFromDateTime(): String{
    if (this == null) return ""
    val fromDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val toDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    val date = fromDate.parse(this)
    return toDate.format(date)
}

fun String?.getFormattedDateTime(): Long {
    if (this == null) 0
    val fromDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val toDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    return fromDate.parse(this).time
}

