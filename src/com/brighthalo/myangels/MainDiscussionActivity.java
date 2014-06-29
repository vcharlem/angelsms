package com.brighthalo.myangels;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Window;

import android.view.WindowManager.LayoutParams;
import com.brighthalo.utilities.SMSBroadcaster;

//import de.svenjacobs.loremipsum.LoremIpsum;

public class MainDiscussionActivity extends Activity {
	private DiscussArrayAdapter adapter;
	private AngelDiscussArrayAdapter lAdapter;
	private ListView lv;
	private EditText editText1;
	private static Random random;
	private SMSBroadcaster smsSender;
	public Button nine11Btn, sendBtn;
	public TextView myTextMsg;
	public String msgReceived;
	public String sendPhoneNumber;
	public ArrayList<Angel> listOfAngels = new ArrayList<Angel>();
	
	public Hashtable<String, Integer> hashtable = new Hashtable<String, Integer>();

	private BroadcastReceiver myReceiver;
	public IntentFilter smsIntent;
	Window wind;

	public TextView sometext;
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
			View lineView = lv.getChildAt(hashtable.get(sendPhoneNumber));
			
			sometext = (TextView) lineView.findViewById(R.id.comment);
			sometext.setText(msgReceived);
			
			Log.d(Constants.DeBugTAG, "MainDiscussion Got this message: " + msgReceived);
			Log.d(Constants.DeBugTAG, "MainDiscussion from phone: " + sendPhoneNumber);
			//adapter.add(new OneComment(false, msgReceived));
		} catch (NullPointerException  e) { }
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SharedStorage sharedStorage = new SharedStorage(MainDiscussionActivity.this);
		listOfAngels = getIntent().getParcelableArrayListExtra("listOfAngels");

		setContentView(R.layout.main_discussion_activity);
		setGlobalBtnControls();
		setLocalBtnControls();
		smsSender = new SMSBroadcaster(MainDiscussionActivity.this);
		lv = (ListView) findViewById(R.id.listView1);

	  //adapter = new DiscussArrayAdapter(getApplicationContext(), R.layout.listitem_discuss);
		lAdapter = new AngelDiscussArrayAdapter(getApplicationContext(), R.layout.listitem_discuss);
		lv.setAdapter(lAdapter);
		
		if(listOfAngels != null) {
			if (!listOfAngels.isEmpty()) {
				addAngelsToThisView();
				return;
			}
		}
		
		if(!sharedStorage.getAngelList().isEmpty()) {
			listOfAngels = sharedStorage.getAngelList();
			addAngelsToThisView();
			return;
		}
	}

	private void addAngelsToThisView() {
		//adapter.add(new OneComment(true, "Hi Angels, do you know how to change a tire?!"));
		for (int x=0; x<listOfAngels.size(); x++){
			Log.d(Constants.DeBugTAG, "MainDiscussion gets listOfAngels: " + listOfAngels.get(x).getName());
			lAdapter.add(listOfAngels.get(x));
			hashtable.put(listOfAngels.get(x).getPhoneNumber(), x);
		}
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
	public String outGoingTextMsg;
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
						//adapter.add(new OneComment(false, myTextMsg.getText().toString()));
						outGoingTextMsg = myTextMsg.getText().toString();
						//myTextMsg.setText("");
						return true;
					}
					return false;
				}
			});

		 	sendBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//smsSender.sendSmsByManager("508-322-1010", "Test from MyAngels");
					initiatePopupWindow();
				}
		 	});
	}
	
	
	public PopupWindow pwindo;
	public Button btnClosePopup;
	public TextView popupTxt;
	public void initiatePopupWindow() {
		try {
			// We need to get the instance of the LayoutInflater
			LayoutInflater inflater = (LayoutInflater) MainDiscussionActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View layout = inflater.inflate(R.layout.popup_msg, (ViewGroup) findViewById(R.id.popup_element));
			pwindo = new PopupWindow(layout, 300, 370, true);
			pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);

			btnClosePopup = (Button) layout.findViewById(R.id.btn_close_popup);
			btnClosePopup.setOnClickListener(cancel_button_click_listener);
			popupTxt = (TextView) layout.findViewById(R.id.txtView);
			popupTxt.setText(outGoingTextMsg);

			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	private OnClickListener cancel_button_click_listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
		pwindo.dismiss();

		}
	};

	public void setGlobalBtnControls(){
		  Button skipBtn, doneBtn, sendBtn;//, nine11Btn;
		  TextView screenTitle, myTextMsg;
		  skipBtn     = (Button)   findViewById(R.id.button_skip);
		  doneBtn     = (Button)   findViewById(R.id.button_done);
		  screenTitle = (TextView) findViewById(R.id.activity_title);
		  
		  screenTitle.setText("Chat");

		  skipBtn.setVisibility(View.GONE);
		  doneBtn.setVisibility(View.GONE);
		  screenTitle.setVisibility(View.GONE);
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

    	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
        	case R.id.menu_settings:
        		Toast.makeText(this, "You pressed Menu Settings!",
                     Toast.LENGTH_LONG).show();
        		return true;
        	case R.id.angel_groupsettings:
        		Intent intent=new Intent(MainDiscussionActivity.this,AngelGroupSetupActivity.class);
        		startActivity(intent);
        		finish();
        		return true;
        	//	Toast.makeText(this, "You pressed the Setting!",
        	//				Toast.LENGTH_LONG).show(); break;
        	default:
        		return super.onOptionsItemSelected(item);
        }
    }    
}