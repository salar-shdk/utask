package com.example.salar.cevent

import android.app.AlertDialog
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.example.salar.cevent.R
import kotlinx.android.synthetic.main.dialog_add.*

/**
 * Created by shayan4shayan on 1/24/18.
 * dialog for add new Account
 */
class AddDialog(c: Context, private val callback: AddDialog.Callback) : AlertDialog(c), View.OnClickListener {
    /**
     * click listener for add button
     */
    override fun onClick(p0: View?) {
        //get username from user
        val username = text_username.text.toString()
        //get password from user
        val password = text_password.text.toString()
        //validate user inputs
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(context, "Error : username or password is Empty", Toast.LENGTH_SHORT).show()
            return
        }
        //call activity method
        callback.add(username, password)
        //dismiss activity
        dismiss()
    }

    /**
     * show dialog and setContentView
     */
    override fun show() {
        super.show()
        //removing flags to be able get input in dialog
        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
        setContentView(R.layout.dialog_add)
        //set click listener to button
        btn_add.setOnClickListener(this)
        text_username.setTypeface(2)
        text_password.setTypeface(2)
    }

    /**
     * call back for send result to activity
     */
    interface Callback {
        fun add(username: String, password: String)
    }

}