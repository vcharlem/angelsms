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
import android.os.CountDownTimer;
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
import com.brighthalo.utilities.SMSBroadcaster;
//import de.svenjacobs.loremipsum.LoremIpsum;

public class MainDiscussionActivity extends Activity {
	public DiscussArrayAdapter adapter;
	public static AngelDiscussArrayAdapter lAdapter;
	public static ListView lv;
	public EditText editText1;
	public static Random random;
	public SMSBroadcaster smsSender;
	public Button nine11Btn, sendBtn;
	public TextView myTextMsg;
	public String msgReceived;
	public String sendPhoneNumber;
	public ArrayList<Angel> listOfAngels = new ArrayList<Angel>();
	public Hashtable<String, Integer> hashtable = new Hashtable<String, Integer>();

	public BroadcastReceiver myReceiver;
	public IntentFilter smsIntent;
	Window wind;
	public String sms_phoneNumbers;
	public static TextView commentUpdate;
	public static View lineView;
	public View myRow;
	public SharedStorage sharedStorage;
	
	@Override
	protected void onPause(){
		super.onPause();
		sharedStorage.setAngelList(listOfAngels);
	}
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
	  super.onRestoreInstanceState(savedInstanceState);
	  notify();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
	  super.onSaveInstanceState(outState);
	//  outState.putAll(outState);
	  notify();
	}
	  
	@Override
	protected void onResume() {
		super.onResume();

		wind = this.getWindow();
		wind.addFlags(LayoutParams.FLAG_DISMISS_KEYGUARD);
		wind.addFlags(LayoutParams.FLAG_SHOW_WHEN_LOCKED);
		wind.addFlags(LayoutParams.FLAG_TURN_SCREEN_ON);
		Intent mFilterIntent = new Intent();
		mFilterIntent = getIntent();
		try{
			if(mFilterIntent.getAction().equals(Constants.SMS_INTENT)){
				sendPhoneNumber = getIntent().getExtras().getString("senderPhone");
				msgReceived = getIntent().getExtras().getString("senderMsg");
			}
			
			//lv.setAdapter(MainDiscussionActivity.lAdapter);

				if(lv != null) {
					for(int x=0; x<listOfAngels.size(); x++){
					//	lAdapter.getItem(hashtable.get(x)).setLastComment(listOfAngels.get(x).getLastComment() );
						Log.d(Constants.DeBugTAG, "From onResume, last comment from  Angel: " + 
									listOfAngels.get(x).getName() + ":" + listOfAngels.get(x).getLastComment() );

						//listOfAngels.get(x).setLastComment(listOfAngels.get(x).getLastComment() );
				}
				
				//lAdapter.getItem(hashtable.get(sendPhoneNumber)).setLastComment(msgReceived);
				listOfAngels.get(hashtable.get(sendPhoneNumber)).setLastComment(msgReceived);
				
				//lineView = lv.getChildAt(hashtable.get(sendPhoneNumber));
				//commentUpdate = (TextView) lineView.findViewById(R.id.comment);
				//commentUpdate.setText(msgReceived); 
				//lAdapter.notifyDataSetChanged();
			} else {
				Log.d(Constants.DeBugTAG, "+++ MainDiscussionActivity View lv returned NULL");
			}

			//lAdapter.notifyDataSetChanged();
			//adapter.add(new OneComment(false, msgReceived));
		} catch (NullPointerException  e) { 				
			e.printStackTrace();
		  }
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {  
		super.onCreate(savedInstanceState);
		lv = new ListView(MainDiscussionActivity.this);
		sharedStorage = new SharedStorage(MainDiscussionActivity.this);
		listOfAngels = getIntent().getParcelableArrayListExtra("listOfAngels");

		setContentView(R.layout.main_discussion_activity);
		setGlobalBtnControls();
		setLocalBtnControls();
		
		smsSender = new SMSBroadcaster(MainDiscussionActivity.this);

		lAdapter = new AngelDiscussArrayAdapter(getApplicationContext(), R.layout.listitem_discuss);
		lv = (ListView) findViewById(R.id.listView1);
		lv.setAdapter(lAdapter);
		
		if(listOfAngels != null) if (!listOfAngels.isEmpty()) { addAngelsToThisView(); }
		
		if(sharedStorage.isAngelListCreated()) {
			listOfAngels = sharedStorage.getAngelList();
			addAngelsToThisView();
		}
		if(lineView == null)
			Log.d(Constants.DeBugTAG, "====== OnCreate MainDiscussionActivity View lineView returned NULL");
		else 
			Log.d(Constants.DeBugTAG, "====== OnCreate MainDiscussionActivity View lineView returned Something");
	}
	
	private void addAngelsToThisView() {
		//adapter.add(new OneComment(true, "Hi Angels, do you know how to change a tire?!"));
		for (int x = 0; x < listOfAngels.size(); x++){
			Log.d(Constants.DeBugTAG, "MainDiscussion Resolves hash index : " + x + " to: " + listOfAngels.get(x).getPhoneNumber());
			lAdapter.add(listOfAngels.get(x));
			hashtable.put(listOfAngels.get(x).getPhoneNumber(), x);
		}
	}

	public String outGoingTextMsg;
	public void setLocalBtnControls(){
		 sendBtn	  = (Button)   findViewById(R.id.quit);
		 myTextMsg	  = (TextView) findViewById(R.id.myTextMsg);
		 nine11Btn	  = (Button)   findViewById(R.id.about);
		 nine11Btn.setText("Call 911");
		 sendBtn.setText("Send");	

		 nine11Btn.setOnClickListener( new OnClickListener() {
			int x = 0;
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Log.d(Constants.DeBugTAG, "From Nine11Button, get last comment from  Angel: " + 
						listOfAngels.get(x).getName() + ":" + listOfAngels.get(x).getLastComment() );

				listOfAngels.get(x).setLastComment("Street Rossi");

				lineView = lv.getChildAt(x);//hashtable.get(sendPhoneNumber));
				commentUpdate = (TextView) lineView.findViewById(R.id.comment);
				commentUpdate.setText("Street Rossi");
				x++;
			}
		 });

		 myTextMsg.setOnKeyListener(new OnKeyListener() {
				public boolean onKey(View v, int keyCode, KeyEvent event) {
					// If the event is a key-down event on the "enter" button
					if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
						// Perform action on key press
						//adapter.add(new OneComment(false, myTextMsg.getText().toString()));
						outGoingTextMsg = myTextMsg.getText().toString();
						return true;
					}
					return false;
				}
			});

		 	sendBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					outGoingTextMsg = myTextMsg.getText().toString();
					Log.d(Constants.DeBugTAG, "Outgoing message string: " + outGoingTextMsg);

					initiatePopupWindow();

					for(int x=0;  x < listOfAngels.size(); x++ ) {
						Log.d(Constants.DeBugTAG, "SendButton string: " + listOfAngels.get(x).getPhoneNumber());
						//smsSender.sendSmsByManager(listOfAngels.get(x).getPhoneNumber(), outGoingTextMsg);
					}
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
			pwindo.showAtLocation(layout, Gravity.BOTTOM, 0, 0);

			btnClosePopup = (Button) layout.findViewById(R.id.btn_close_popup);
			btnClosePopup.setOnClickListener(cancel_button_click_listener);
			btnClosePopup.setText("Ok");
			
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
	private static int getRandomInteger(int aStart, int aEnd) {
		if (aStart > aEnd) {
			throw new IllegalArgumentException("Start cannot exceed End.");
		}
		long range = (long) aEnd - (long) aStart + 1;
		long fraction = (long) (range * random.nextDouble());
		int randomNumber = (int) (fraction + aStart);
		return randomNumber;
	}

	private class MyCount extends CountDownTimer {
		public MyCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}
		@Override
		public void onFinish() {
			return;
		}
		@Override
		public void onTick(long millisUntilFinished) {
			// TODO Auto-generated method stub
		}
	}



}