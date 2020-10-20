package com.example.gestureunlock

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Build
import android.text.Html
import android.text.Spanned
import com.example.gestureunlock.data.File
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat


@SuppressLint("SimpleDateFormat")
fun convertLongToDateString(systemTime: Long): String {
    return SimpleDateFormat("EEEE MMM-dd-yyyy' Time: 'HH:mm")
        .format(systemTime).toString()
}

fun formatFiles(nights: List<File>, resources: Resources): Spanned {
    val sb = StringBuilder()
    sb.apply {
        append(resources.getString(R.string.title))
        nights.forEach {
            append("<br>")
            append(it.fileName)
            append("<br>")
            append(resources.getString(R.string.created_time))
            append("\t${convertLongToDateString(it.createdTimeMilli)}<br>")
        }
    }
    // fromHtml is deprecated for target API without a flag, but since our minSDK is 19, we
    // can't use the newer version, which requires minSDK of 24
    //https://developer.android.com/reference/android/text/Html#fromHtml(java.lang.String,%20int)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY)
    } else {
        @Suppress("DEPRECATION")
        return Html.fromHtml(sb.toString())
    }
}


interface MyFragmentListener{
    fun getFab(): FloatingActionButton
}