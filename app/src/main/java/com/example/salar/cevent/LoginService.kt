package com.example.salar.cevent

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class LoginService : Service() {
    companion object {
        public var retryCount = 0
    }

    lateinit var queue: RequestQueue

    override fun onCreate() {
        super.onCreate()
        queue = Volley.newRequestQueue(this)
        Log.d("Login Service : ","onCreate")
        checkConnection()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        checkConnection()

        return START_STICKY
    }

    private  fun checkConnection() {
        val request = StringRequest(Request.Method.GET, getCheckUrl(), checkResponseListener, checkErrorListener)
        queue.add(request)
    }

    /**
     * error listener for check internet connection
     */
    private val checkErrorListener = Response.ErrorListener { error -> onError(error) }

    private fun onError(error: VolleyError?) {
        Log.d("WifiReceiver", "Error $error")
        retryCount++
        if(retryCount<20)sendLoginRequest()
        else {
            Log.d("WifiReceiver","no ut wifi detected")

        }
    }

    private val checkResponseListener = Response.Listener<String> { response -> onCheckResponse(response) }

    private fun onCheckResponse(response: String?) {

    }

    /**
     * send login request to hotspot.sbu.ac.ir
     */
    private fun sendLoginRequest() {
        Handler().postDelayed({ performLogin() }, 2000)

    }

    private fun performLogin() {
        Log.d("WifiReceiver", "sending login request")
        val username = MainActivity.getUsername(baseContext)
        val password = MainActivity.getPassword(baseContext)
        val request = getLoginRequest(username,password)
        queue.add(request)

    }

    private val responseListener = Response.Listener<String> { response -> onLoginResponse(response) }

    private fun onLoginResponse(response: String?) {
        Log.d("LoginService", response)
        if (response?.contains("<form name=\"login\"")!!) {
            Log.d("LoginService", "Contains login form")
            checkConnection()
        }
        if(response.contains("You are logged in")){
            Toast.makeText(this, "You are logged in :)", Toast.LENGTH_SHORT).show()
        }
    }

    private val errorListener = Response.ErrorListener { error -> onError(error) }

    /**
     * returns request to hotspot.sbu.ac.ir
     */
    private fun getLoginRequest(username: String, password: String): StringRequest {
        return object : StringRequest(Request.Method.POST, getLoginUrl(), responseListener, errorListener) {
            override fun getParams(): MutableMap<String, String> {
                val map = HashMap<String, String>()
                map["username"] = username
                map["password"] = password
                return map
            }
        }
    }


    /**
     * get account from data base and creates url for login
     * returns url for login
     */
    private fun getLoginUrl(): String {

        return "https://internet.ut.ac.ir/login"
    }

    /**
     * returns url for check internet connection
     */
    private fun getCheckUrl(): String? {
        return "https://clients1.google.com/generate_204"
    }
}
