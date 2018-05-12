package com.example.salar.cevent

import android.content.Context
import android.util.Log
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.jsoup.Jsoup

open class RemainedVolumeLoader(val context: Context, val callback: Callback) : Response.ErrorListener, Response.Listener<String> {
    override fun onResponse(response: String?) {
        Log.d("VOLUME: \n", response)
        try {
            val parser = Jsoup.parse(response)
            val body = parser.body()

            val unit = body.child(15).child(0).child(0)
                    .child(0).child(0).child(0)
                    .child(2).child(7)
            Log.d("VOLUME", "$unit")

            callback.onLoaded(unit.ownText())
        } catch (e: Exception) {

        }
    }

    override fun onErrorResponse(error: VolleyError?) {
        if (error?.networkResponse == null) {
            Log.d("VOLUME", "NULL")
            return
        }
        //requestForVolume(null,null)
        Log.d("VOLUME : \n", error.toString())
    }


    private val queue = Volley.newRequestQueue(context)

    fun startLoad(username: String, password: String) {

        HttpTrustManager.allowAllSSL()

        val req = object : StringRequest(Request.Method.POST, getUrl(), this, this) {
            override fun getParams(): MutableMap<String, String> {
                val map = HashMap<String, String>()
                map["normal_username"] = username
                map["normal_password"] = password
                map["lang"] = "fa"
                map["x"] = "21"
                map["y"] = "17"
                return map
            }

            override fun getHeaders(): MutableMap<String, String> {
                val map = HashMap<String, String>()
                map["Cookie"] = "IBS_SESSID=p5p2t3nq4gdiv09amjvkqgaqj0; session_id=d633eb05fc8f0554d10e1fe675bf24ba701b982f"
                map["IBS_SESSID"] = "p5p2t3nq4gdiv09amjvkqgaqj0"
                map["session_id"] = "d633eb05fc8f0554d10e1fe675bf24ba701b982f"
                return map
            }
        }

        req.retryPolicy = DefaultRetryPolicy(0, -1, 1f)

        queue.add(req)
    }

    fun getUrl() = "http://acct.ut.ac.ir/IBSng/user/"

    public interface Callback {
        fun onLoaded(remained: String)
    }
}