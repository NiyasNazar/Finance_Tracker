package com.pas.financetracker.utils


import android.os.SystemClock
import android.view.View
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

object AppUtils  {
    fun View.singleClickListener(onClickListener: View.OnClickListener) {
        safeClick(onClickListener, 1000L)
    }
    fun View.safeClick(listener: View.OnClickListener, blockInMillis: Long = 500) {
        var lastClickTime: Long = 0
        this.setOnClickListener {
            if (SystemClock.elapsedRealtime() - lastClickTime < blockInMillis) return@setOnClickListener
            lastClickTime = SystemClock.elapsedRealtime()
            listener.onClick(this)
        }
    }
    fun formatDate(year: Int, month: Int, dayOfMonth: Int): Pair<String, String> {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)

        val isoFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        isoFormat.timeZone = TimeZone.getTimeZone("UTC")


        val simpleFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        val isoDate = isoFormat.format(calendar.time)
        val formattedDate = simpleFormat.format(calendar.time)

        return Pair(isoDate, formattedDate)
    }
}
