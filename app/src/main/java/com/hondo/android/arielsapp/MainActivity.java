package com.hondo.android.arielsapp;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";
	private SharedPreferences mPrefs;
	private String mPhone;
	private String mMsg;
	private String mName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		displayMainScreen();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void updatePreferences(View v) {
		Intent intent = new Intent();
		intent.setClass(this, Options.class);
		v.invalidate();
		startActivityForResult(intent, 0);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		displayMainScreen();
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void sendMessage(View v) {
		// Sends the appropriate message depending on which button was pressed
		int button = v.getId();
		Log.d(TAG, "sendMessage button is " + button);
		String msg = "Invalid Button";

		if (button == R.id.coffee) {
			msg = "Let's get some coffee";
		} else if (button == R.id.fiddle) {
			msg = "Quit fiddling with your phone";
		} else if (button == R.id.milk) {
			msg = "Please pick up a gallon of milk";
		} else if (button == R.id.smokes) {
			msg = "Get us some smokes on your way home";
		} else if (button == R.id.leaving) {
			msg = "I'm leaving work now, see you soon!";
		} else if (button == R.id.custom) {
			msg = mMsg;
		}

		Log.d(TAG, "message is " + msg);
		// Intent i = new Intent(Intent.ACTION_SEND);
		// i.setType("text/plain");
		// i.putExtra(Intent.EXTRA_TEXT, msg);
		// startActivity(i);

		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.fromParts("sms", mPhone, null));
		i.putExtra("sms_body", msg);

		// Intent i = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto",
		// "7082887479@messaging.sprintpcs.com", null));
		// i.putExtra(Intent.EXTRA_TEXT, msg);

		Log.d(TAG,
				"resolve activity is "
						+ i.resolveActivity(this.getPackageManager()));
		if (i.resolveActivity(this.getPackageManager()) == null) {
			Toast.makeText(getApplicationContext(),
					"No texting client configured!", Toast.LENGTH_LONG).show();
		} else {
			startActivity(Intent.createChooser(i,
					"Please choose your texting app"));
		}
		// try {
		// startActivity(i);
		// } catch (Exception ex) {
		// Toast.makeText(this.getApplicationContext(), "Cannot send message",
		// Toast.LENGTH_LONG).show();
		// Log.e(TAG, "sendMessage startActivity error", ex);
		// }
	}

	public void displayMainScreen() {
		setContentView(R.layout.activity_main);
		mPrefs = getSharedPreferences("settings", MODE_PRIVATE);
		mPhone = mPrefs.getString("phone_number", "");
		mMsg = mPrefs.getString("message", "");
		mName = mPrefs.getString("name", "");
		Log.d(TAG, "xmName is :" + mName + ":");
		Log.d(TAG, "xmPhone is :" + mPhone + ":");
		Log.d(TAG, "xmMsg is :" + mMsg + ":");
		TextView headerView = (TextView) findViewById(R.id.header);
		Button customView = (Button) findViewById(R.id.custom);
		if (!mName.isEmpty() && mName != null) {
			headerView.setText(getString(R.string.quick_message_header)
					+ " for " + mName);
		}

		if (mPhone == null || mPhone == "") {
			Toast.makeText(
					getApplicationContext(),
					"You haven't added the phone number you'd like to send to. Please press on the preferences button",
					Toast.LENGTH_LONG).show();
		}

		if (!mMsg.isEmpty() && mMsg != null) {
			customView.setText(mMsg);
			customView.setVisibility(Button.VISIBLE);
		}

	}

}
