package com.example.salar.cevent

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import java.util.*

/**
 * Created by salar on 5/12/18.
 */
class BootReceiver: BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        registerReminder(p0)
    }

    private fun registerReminder(p0: Context?) {
        val cal = Calendar.getInstance()
        val day = cal.get(Calendar.DAY_OF_WEEK)

        val wed = Calendar.WEDNESDAY

        var diff = wed - day
        if (diff < 0) diff += 7


        val manager = p0?.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        manager.cancel(getPendingIntent(p0))

        manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + getDayInMiliSec(diff), getDayInMiliSec(7).toLong(), getPendingIntent(p0))
    }

    private fun getPendingIntent(p0: Context?): PendingIntent {
        val intent = Intent(p0, Receiver::class.java)
        return PendingIntent.getBroadcast(p0, 0, intent, PendingIntent.FLAG_ONE_SHOT)
    }

    private fun getDayInMiliSec(day: Int) = if (day == 0) 60 else day * 24 * 60 * 60 * 1000


}