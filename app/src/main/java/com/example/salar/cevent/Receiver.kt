package com.example.salar.cevent;

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class Receiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        NotificationUtil.sendNotification(p0)
    }

}