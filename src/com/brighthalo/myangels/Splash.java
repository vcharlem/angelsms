package com.brighthalo.myangels;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import com.brighthalo.myangels.SharedStorage;

public class Splash extends Activity {//InstrumentedActivity {
	String  prefName;
	Boolean isTermConditionAccepted;
	Boolean angelListCreated;
	Boolean viewedInstructionPage;
	public enum userState {
		NoTermNoInstructionNoAngelList,
		YesTermYesInstructionYesAngelList,
		YesAngelListCreated
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		SharedStorage sharedStorage = new SharedStorage(Splash.this);
		isTermConditionAccepted = true; //sharedStorage.getAcceptance();
		viewedInstructionPage = sharedStorage.getInstructionSeen();		
		angelListCreated = sharedStorage.isAngelListCreated();
		
		// use for fetching UA APID for registration String apid = PushManager.shared().getAPID();
        // Log.d("myAngels","myAngels onCreate - App APID: " + apid);
		MyCount mc = new MyCount(2000, 1000);
		mc.start();
	}
	public userState checkUserState(){
		if( angelListCreated) 
			return userState.YesAngelListCreated;

		if(!angelListCreated && !viewedInstructionPage)
			return userState.NoTermNoInstructionNoAngelList;

		if(isTermConditionAccepted && viewedInstructionPage && angelListCreated )
			return userState.YesTermYesInstructionYesAngelList;
		
		return userState.NoTermNoInstructionNoAngelList;
	}
	public class MyCount extends CountDownTimer {
		public MyCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}
		@Override
		public void onFinish() {
			switch(checkUserState()){
				case NoTermNoInstructionNoAngelList:
					startActivity(new Intent(Splash.this, InstructionActivity.class));
					finish();	
				break;
				case YesTermYesInstructionYesAngelList:
				break;
				case YesAngelListCreated:
					startActivity(new Intent(Splash.this, MainDiscussionActivity.class));
					finish();	
				break;
				default:
					startActivity(new Intent(Splash.this, AngelGroupSetupActivity.class));
					finish();
				break;
			}
			/*
			if(angelListCreated) {
				startActivity(new Intent(Splash.this, InstructionActivity.class));
				//startActivity(new Intent(Splash.this, AngelGroupSetupActivity.class));
				finish();			
			} else {
				startActivity(new Intent(Splash.this, InstructionActivity.class));
				finish();	
			
				if (isTermConditionAccepted) {
					startActivity(new Intent(Splash.this, LangSelectionActivity.class));
					finish();
				}
				if(!isTermConditionAccepted) {
					startActivity(new Intent(Splash.this, LangSelectionActivity.class));
					finish();
				}
				http://stackoverflow.com/questions/8850497/switch-case-request-with-boolean
			}*/
		}
		@Override
		public void onTick(long millisUntilFinished) {
		}
	}
}