package com.hondo.android.arielsapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Menu;
import android.widget.EditText;

public class Options extends Activity {

	private static final String TAG = "Options";
	private SharedPreferences mPrefs;
	private String mName;
	private String mPhone;
	private String mMsg;
	private EditText nameView;
	private EditText phoneView;
	private EditText msgView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_options);
		Log.d(TAG, "onCreate");
		
		mPrefs = getSharedPreferences("settings", MODE_PRIVATE);
		mName = mPrefs.getString("name", "");
		mPhone = mPrefs.getString("phone_number", "");
		mMsg = mPrefs.getString("message", "");
		Log.d(TAG, "mName is :" + mName + ":");
		Log.d(TAG, "mPhone is :" + mPhone + ":");
		Log.d(TAG, "mMsg is :" + mMsg + ":");
		
		nameView = (EditText)findViewById(R.id.name_edit);
		phoneView = (EditText)findViewById(R.id.phone_edit);
		msgView = (EditText)findViewById(R.id.message_edit);
		
		if (mName != null && mName != "") {
			nameView.setHint(mName);
		}
		if (mPhone != null && mPhone != "") {
			phoneView.setHint(mPhone);
		}
		if (mMsg != null && mMsg != "") {
			msgView.setHint(mMsg);
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.options, menu);
		return true;
	}

	@Override
	public void onBackPressed() {
		
		Editor editor = mPrefs.edit();
		
		String test = nameView.getText().toString(); 
		Log.d(TAG, "getText is :" + test + ":");
		
		Log.d(TAG, "nameView.getText is :" + test + ":");
		Log.d(TAG, "mName is :" + mName + ":");
		Log.d(TAG, "test length is :" + test.length());
		if (test.isEmpty() || test == null) {
			editor.putString("name", mName);
		} else {
			editor.putString("name", test);
		}
		
		test = phoneView.getText().toString(); 
		if (test.isEmpty() || test == null) {
			editor.putString("phone_number", mPhone);
		} else {
			editor.putString("phone_number", test);
		}
		
		test = msgView.getText().toString(); 
		Log.d(TAG, "getText is :" + test + ":");
		if (test.isEmpty() || test == null) {
			editor.putString("message", mMsg);
		} else {
			editor.putString("message", test);
		}
		
		editor.commit();
		onCreate(null);
		
	
		super.onBackPressed();
	}
	

}
