package com.hondo.android.arielsapp

import android.os.Bundle
import android.app.Activity
import android.content.Context
import android.content.Context.*
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.util.Log
import android.view.Menu
import android.widget.EditText

class Options : Activity() {
    private var mPrefs: SharedPreferences? = null
    private var mName: String? = null
    private var mPhone: String? = null
    private var mMsg: String? = null
    private var nameView: EditText? = null
    private var phoneView: EditText? = null
    private var msgView: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)
        Log.d(TAG, "onCreate")

        mPrefs = getSharedPreferences("settings", Context.MODE_PRIVATE)
        mName = mPrefs!!.getString("name", "")
        mPhone = mPrefs!!.getString("phone_number", "")
        mMsg = mPrefs!!.getString("message", "")
        Log.d(TAG, "mName is :$mName:")
        Log.d(TAG, "mPhone is :$mPhone:")
        Log.d(TAG, "mMsg is :$mMsg:")

        nameView = findViewById(R.id.name_edit) as EditText
        phoneView = findViewById(R.id.phone_edit) as EditText
        msgView = findViewById(R.id.message_edit) as EditText

        if (mName != null && mName !== "") {
            nameView!!.hint = mName
        }
        if (mPhone != null && mPhone !== "") {
            phoneView!!.hint = mPhone
        }
        if (mMsg != null && mMsg !== "") {
            msgView!!.hint = mMsg
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.options, menu)
        return true
    }

    override fun onBackPressed() {

        val editor = mPrefs!!.edit()

        var test: String? = nameView!!.text.toString()
        Log.d(TAG, "getText is :$test:")

        Log.d(TAG, "nameView.getText is :$test:")
        Log.d(TAG, "mName is :$mName:")
        Log.d(TAG, "test length is :" + test!!.length)
        if (test.isEmpty() || test == null) {
            editor.putString("name", mName)
        } else {
            editor.putString("name", test)
        }

        test = phoneView!!.text.toString()
        if (test.isEmpty() || test == null) {
            editor.putString("phone_number", mPhone)
        } else {
            editor.putString("phone_number", test)
        }

        test = msgView!!.text.toString()
        Log.d(TAG, "getText is :$test:")
        if (test.isEmpty() || test == null) {
            editor.putString("message", mMsg)
        } else {
            editor.putString("message", test)
        }

        editor.commit()
        onCreate(null)


        super.onBackPressed()
    }

    companion object {

        private val TAG = "Options"
    }


}
