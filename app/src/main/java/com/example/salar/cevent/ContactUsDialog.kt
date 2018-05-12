package com.example.salar.cevent

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import kotlinx.android.synthetic.main.contact_us.*
import kotlinx.android.synthetic.main.dialog_add.*

/**
 * Created by salar on 5/12/18.
 */
class ContactUsDialog(c: Context) : AlertDialog(c) {

    override fun show() {
        super.show()
        setContentView(R.layout.contact_us)
        //set click listener to button
        name_salar.setTypeface(2)
        name_khojir.setTypeface(2)
        mail_salar.setOnClickListener(View.OnClickListener {
            sendEmail("amir.shdk@yahoo.com")
        })
        mail_khojir.setOnClickListener(View.OnClickListener {
            sendEmail("h.fazli.k@gmail.com")
        })
    }




    fun sendEmail(email:String){
        val i = Intent(Intent.ACTION_SEND)
        i.type = "message/rfc822"
        i.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        i.putExtra(Intent.EXTRA_SUBJECT, "uTask - user review")
        try {
            context.startActivity(Intent.createChooser(i, "Send mail..."))
        } catch (ex: android.content.ActivityNotFoundException) {

        }

    }



}