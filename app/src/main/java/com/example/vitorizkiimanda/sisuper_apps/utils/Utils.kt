package com.example.vitorizkiimanda.sisuper_apps.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun formatDate(date: String?): String{
    val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    return SimpleDateFormat("EEEE, dd MMM yyyy").format(dateFormat.parse(date) as Date)
}