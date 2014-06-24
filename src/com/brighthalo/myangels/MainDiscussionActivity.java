package com.brighthalo.myangels;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.view.Window;

import android.view.WindowManager.LayoutParams;
import com.brighthalo.utilities.SMSBroadcaster;

//import de.svenjacobs.loremipsum.LoremIpsum;

public class MainDiscussionActivity extends Activity {
	private DiscussArrayAdapter adapter;
	private ListView lv;
	private EditText editText1;
	private static Random random;
	private SMSBroadcaster smsSender;
	public Button nine11Btn, sendBtn;
	public TextView myTextMsg;
	public String msgReceived;
	public String sendPhoneNumber;
	public ArrayList<Angel> listOfAngels = new ArrayList<Angel>();
	private BroadcastReceiver myReceiver;
	public IntentFilter smsIntent;
	Window wind;
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		//wind = this.getWindow();
		//wind.addFlags(LayoutParams.FLAG_DISMISS_KEYGUARD);
		//wind.addFlags(LayoutParams.FLAG_SHOW_WHEN_LOCKED);
		//wind.addFlags(LayoutParams.FLAG_TURN_SCREEN_ON);

		try{
			sendPhoneNumber = getIntent().getExtras().getString("senderPhone");
			msgReceived = getIntent().getExtras().getString("senderMsg");
			Log.d(Constants.DeBugTAG, "MainDiscussion Got this message: " + msgReceived);
			Log.d(Constants.DeBugTAG, "MainDiscussion from phone: " + sendPhoneNumber);
			adapter.add(new OneComment(false, msgReceived));
		} catch (NullPointerException  e) {
			
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		listOfAngels = getIntent().getParcelableArrayListExtra("listOfAngels");
		
		for (int x=0; x<listOfAngels.size(); x++){
			Log.d(Constants.DeBugTAG, "MainDiscussion gets listOfAngels: " + listOfAngels.get(x).getName());

		}
		
		setContentView(R.layout.main_discussion_activity);
		setGlobalBtnControls();
		setLocalBtnControls();
		smsSender = new SMSBroadcaster(MainDiscussionActivity.this);
		lv = (ListView) findViewById(R.id.listView1);

		adapter = new DiscussArrayAdapter(getApplicationContext(), R.layout.listitem_discuss);
		lv.setAdapter(adapter);
		
		addItems();
	}

	private void addItems() {
		adapter.add(new OneComment(true, "Hi Angels, do you know how to change a tire?!"));
	}

	private static int getRandomInteger(int aStart, int aEnd) {
		if (aStart > aEnd) {
			throw new IllegalArgumentException("Start cannot exceed End.");
		}
		long range = (long) aEnd - (long) aStart + 1;
		long fraction = (long) (range * random.nextDouble());
		int randomNumber = (int) (fraction + aStart);
		return randomNumber;
	}
	
	public void setLocalBtnControls(){
		 sendBtn	  = (Button)   findViewById(R.id.quit);
		 myTextMsg	  = (TextView) findViewById(R.id.myTextMsg);
		 nine11Btn	  = (Button)   findViewById(R.id.about);
		 nine11Btn.setText("Call 911");
		 sendBtn.setText("Send");	
		 
		 myTextMsg.setOnKeyListener(new OnKeyListener() {
				public boolean onKey(View v, int keyCode, KeyEvent event) {
					// If the event is a key-down event on the "enter" button
					if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
						// Perform action on key press
						adapter.add(new OneComment(false, myTextMsg.getText().toString()));
						myTextMsg.setText("");
						return true;
					}
					return false;
				}
			});

		 	sendBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					smsSender.sendSmsByManager("508-322-1010", "Test from MyAngels");
				}
		 	});

	}
	public void setGlobalBtnControls(){
		  Button skipBtn, doneBtn, sendBtn;//, nine11Btn;
		  TextView screenTitle, myTextMsg;
		  skipBtn     = (Button)   findViewById(R.id.button_skip);
		  doneBtn     = (Button)   findViewById(R.id.button_done);
		  screenTitle = (TextView) findViewById(R.id.activity_title);
		  
		  screenTitle.setText("Chat");

		  skipBtn.setOnClickListener(new OnClickListener() {
	  		public void onClick(View v) {
	  			 Intent intent1 = new Intent(MainDiscussionActivity.this,Splash.class);
	             startActivity(intent1);
	  		}
	  	  });
	      
	      doneBtn.setOnClickListener(new OnClickListener() {
	  		public void onClick(View v) {
	  			 Intent intent1 = new Intent(MainDiscussionActivity.this,Splash.class);
	             startActivity(intent1);
	  		}
	  	  });
		}
	
   
    
    /*	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/
}