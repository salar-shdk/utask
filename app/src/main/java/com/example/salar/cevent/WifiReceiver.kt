package com.example.salar.cevent;

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley


/**
 * Created by shayan4shayan on 1/24/18.
 * receiver for track wifi connection
 */
class WifiReceiver : BroadcastReceiver() {
    var context: Context? = null
    private var isConnecting = false

    @SuppressLint("WifiManagerPotentialLeak", "UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context?, intent: Intent?) {
        this.context = context
        Log.d("WifiReceiver", "onReceive called")
        val manager = context?.getSystemService(Context.WIFI_SERVICE) as WifiManager
        if (manager.isWifiEnabled) {
            Log.d("WifiReceiver", "Wifi Available")
            if (!isConnecting) {
                LoginService.retryCount=0
                context.startService(Intent(context,LoginService::class.java))
            }
        } else {
            Log.d("WifiReceiver", "wifi not available")
        }

    }
}