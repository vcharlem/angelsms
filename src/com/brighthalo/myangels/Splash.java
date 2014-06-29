package com.brighthalo.myangels;
import com.urbanairship.analytics.InstrumentedActivity;
import com.urbanairship.push.PushManager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import com.brighthalo.myangels.SharedStorage;

public class Splash extends Activity {//InstrumentedActivity {
	String  prefName;
	Boolean isTermConditionAccepted;
	Boolean noAngelsInList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		SharedStorage sharedStorage = new SharedStorage(Splash.this);
		isTermConditionAccepted = sharedStorage.getAcceptance();
		if (sharedStorage.getAngelList() == null)
			noAngelsInList = true;
		else 
			noAngelsInList = false;
		
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
			if(noAngelsInList) {
				startActivity(new Intent(Splash.this, AngelGroupSetupActivity.class));
				finish();			
			} else {
				startActivity(new Intent(Splash.this, MainDiscussionActivity.class));
				finish();	
			/*
				if (isTermConditionAccepted) {
					startActivity(new Intent(Splash.this, LangSelectionActivity.class));
					finish();
				}
				if(!isTermConditionAccepted) {
					startActivity(new Intent(Splash.this, LangSelectionActivity.class));
					finish();
				}
			*/
			}
		}
		@Override
		public void onTick(long millisUntilFinished) {
		}
	}
}