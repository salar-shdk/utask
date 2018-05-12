package com.example.salar.cevent

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import org.jsoup.helper.HttpConnection
import java.util.*

class MainActivity : AppCompatActivity(), RemainedVolumeLoader.Callback,AddDialog.Callback, View.OnClickListener {
    override fun onClick(p0: View?) {
        when(p0?.id){
            main_contact_us.id -> {
                val contact_us_dialog  = ContactUsDialog(this)
                contact_us_dialog.show()
            }
            main_github.id -> {
                Log.d("----------------","github")
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(GITHUB_URL))
                startActivity(browserIntent)
            }
            main_add_account.id -> {
                var dialog =AddDialog(this,this)
                dialog.show()
            }
        }
    }

    override fun add(username: String, password: String) {
        editPreferences(this,username,password)
        show_username.text =username
        show_username.setTypefaceBold(2)
        updateVolume()
        startService(Intent(baseContext, LoginService::class.java))
        main_volume_text.visibility=View.VISIBLE
    }
    lateinit var queue: RequestQueue
    companion object {
        private val USERNAME = "username"
        private val PASSWORD = "password"
        private val VERSION = 1
        private val GITHUB_URL = "https://github.com/salar-shdk/utask"

        fun editPreferences(context: Context, username:String, password:String){
            val preferences : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            var pref : SharedPreferences.Editor = preferences.edit()
            pref.putString(USERNAME,username)
            pref.putString(PASSWORD,password)
            pref.apply()
        }

        fun getUsername(context: Context): String {
            val preferences : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getString(USERNAME,"")
        }

        fun getPassword(context: Context): String {
            val preferences : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getString(PASSWORD,"")
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        main_add_account.setOnClickListener(this)
        if(getUsername(this)!="")show_username.setTypefaceBold(FontedTextView.EXO_2)
        main_remained_volume.setTypefaceBold(2)
        main_volume_unit.setTypefaceBold(2)
        main_contact_us.setOnClickListener(this)
        main_github.setOnClickListener(this)
        queue = Volley.newRequestQueue(this)
        if(getUsername(this)=="")main_volume_text.visibility=View.GONE
        else show_username.text = getUsername(this)

        updateVolume()
        updateCheck()

        if(firstLunch()) registerReminder()
    }



    private fun firstLunch():Boolean {
        val preferences : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val b = preferences.getString("first_lunch","0")
        var pref : SharedPreferences.Editor = preferences.edit()
        pref.putString("first_lunch","1")
        pref.apply()
        return b=="1"
    }

    private fun updateCheck() {
        queue.add(getUpdateCheckRequest())
    }
    val responseListener = Response.Listener<String> {
        response ->
        val jsonObject = JSONObject(response)
        if(Integer.parseInt(jsonObject.getString("update"))> VERSION){
            val url = jsonObject.getString("link")
            val u = UpdateDialog(this)
            u.url=url
            u.show()
        }

    }
    val errorListener = Response.ErrorListener {
        response ->  Log.d("update_check : ", response.toString())
    }
    private fun getUpdateCheckRequest(): Request<*>? {
        val s = StringRequest(Request.Method.GET, "https://khojir.github.io", responseListener, errorListener)
        s.setShouldCache(false)
        return s


//            override fun getParams(): MutableMap<String, String> {
//                val map = HashMap<String, String>()
//                map["version"] = VERSION.toString()
//                return map
//            }

    }


    private fun registerReminder() {
        val cal = Calendar.getInstance()
        val day = cal.get(Calendar.DAY_OF_WEEK)

        val wed = Calendar.WEDNESDAY

        var diff = wed - day
        if (diff < 0) diff += 7


        val manager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        manager.cancel(getPendingIntent())

        manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + getDayInMiliSec(diff), getDayInMiliSec(7).toLong(), getPendingIntent())
    }

    private fun getPendingIntent(): PendingIntent {
        val intent = Intent(this, Receiver::class.java)
        return PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
    }

    private fun getDayInMiliSec(day: Int) = if (day == 0) 60 else day * 24 * 60 * 60 * 1000


    override fun onLoaded(remained: String) {
        val cleaned = remained.replace("UNITS", "").replace(",", "").trim()
        Log.d("Main--", cleaned)
        val sNum = cleaned.replace(" MegaBytes","")

        val num = sNum.toFloat()
        if (Math.abs(num) >= 1024) {
            main_remained_volume.text = String.format("%1.1f", num / 1024)
            main_volume_unit.text = "Gb"

        } else {
            main_remained_volume.text = num.toString().format(1)
            main_volume_unit.text = "Mb"
        }

    }

    private fun updateVolume() {
        val username = getUsername(baseContext)
        val password = getPassword(baseContext)
        val loader = RemainedVolumeLoader(this, this)
        loader.startLoad(username, password)
    }


}


