package com.hondo.android.arielsapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {
    private lateinit var mPrefs: SharedPreferences
    private lateinit var mPhone: String
    private lateinit var mMsg: String
    private lateinit var mName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        displayMainScreen()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    fun updatePreferences(v: View) {
        v.invalidate()
        startActivityForResult(
                Intent(this, Options::class.java),
                0
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        displayMainScreen()
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun sendMessage(v: View) {
        // Sends the appropriate message depending on which button was pressed
        val button = v.id
        Log.d(TAG, "sendMessage button is " + button)
        var msg: String? = "Invalid Button"

        when (button) {
            R.id.coffee -> msg = "Let's get some coffee"
            R.id.fiddle -> msg = "Quit fiddling with your phone"
            R.id.milk -> msg = "Please pick up a gallon of milk"
            R.id.smokes -> msg = "Get us some smokes on your way home"
            R.id.leaving -> msg = "I'm leaving work now, see you soon!"
            R.id.custom -> msg = mMsg
        }

        Log.d(TAG, "message is " + msg!!)

        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.fromParts("sms", mPhone, null)
        i.putExtra("sms_body", msg)

        Log.d(TAG,
                "resolve activity is " + i.resolveActivity(this.packageManager))

        if (i.resolveActivity(this.packageManager) == null) {
            Toast.makeText(applicationContext,
                    "No texting client configured!", Toast.LENGTH_LONG).show()
        } else {
            startActivity(Intent.createChooser(i,
                    "Please choose your texting app"))
        }
    }

    private fun displayMainScreen() {
        setContentView(R.layout.activity_main)
        mPrefs = getSharedPreferences("settings", Context.MODE_PRIVATE)
        mPhone = mPrefs.getString("phone_number", "")
        mMsg = mPrefs.getString("message", "")
        mName = mPrefs.getString("name", "Need Name!")
        Log.d(TAG, "xmName is :$mName:")
        Log.d(TAG, "xmPhone is :$mPhone:")
        Log.d(TAG, "xmMsg is :$mMsg:")
        val headerView = findViewById(R.id.header) as TextView
        val customView = findViewById(R.id.custom) as Button
        if (!mName.isBlank()) {
            headerView.text = (getString(R.string.quick_message_header)
                    + " for " + mName)
        }

        if (mPhone.isBlank()) {
            Toast.makeText(
                    applicationContext,
                    "You haven't added the phone number you'd like to send to. Please press on the preferences button",
                    Toast.LENGTH_LONG).show()
        }

        if (!mMsg.isBlank()) {
            customView.text = mMsg
            customView.visibility = Button.VISIBLE
        }
    }

    companion object {

        private val TAG = "MainActivity"
    }

}
