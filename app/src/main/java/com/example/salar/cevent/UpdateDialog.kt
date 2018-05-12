package com.example.salar.cevent

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.view.View
import android.view.WindowManager
import kotlinx.android.synthetic.main.dialog_update.*

/**
 * Created by salar on 5/12/18.
 */

class UpdateDialog(c: Context) : AlertDialog(c) {
    lateinit var url:String


    override fun show() {
        super.show()
        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
        setContentView(R.layout.dialog_update)
        update_dialog_text.text = "New Version Available\n" +
                "Try It Now"
        update_dialog_download.setOnClickListener(View.OnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(browserIntent)
        })
    }

}