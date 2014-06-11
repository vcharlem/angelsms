package com.brighthalo.myangels;
import com.urbanairship.analytics.InstrumentedActivity;
import com.urbanairship.push.PushManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.app.Activity;
import android.view.Menu;

public class Splash extends InstrumentedActivity {
	String prefName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		SharedPreferences myPrefs = this.getSharedPreferences("myPrefs",MODE_WORLD_READABLE);
		prefName = myPrefs.getString("accept", "");
		// use for fetching UA APID for registration String apid = PushManager.shared().getAPID();
        // Log.d("myAngels","myAngels onCreate - App APID: " + apid);
		MyCount mc = new MyCount(2000, 1000);
		mc.start();
	}

	private class MyCount extends CountDownTimer {
		public MyCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}
 
		@Override
		public void onFinish() {
			if (prefName.equalsIgnoreCase("")) {
				startActivity(new Intent(Splash.this, Registration.class));
				finish();
			}
			if(!prefName.equalsIgnoreCase("")) {
				startActivity(new Intent(Splash.this, Registration.class));
				finish();
			}
		}

		@Override
		public void onTick(long millisUntilFinished) {
		}
	}
}