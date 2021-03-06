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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Window;
import com.brighthalo.utilities.SMSBroadcaster;
//import de.svenjacobs.loremipsum.LoremIpsum;

/**
 * @author vlad
 * MainDiscussionActivity presents discussion to User.
 * Has Broadcast receiver which listens for SMSs.
 * Uses SMS senderPhoneNumber to look up which ListView child to insert data.
 * 
 * Attempts to use ListView.getChildAt to make inserts were unsuccessful.
 * Fix was to add lastComment field in Angel and Adapter class.
 * 
 * Todo: Fix bug which causes crash on initial startup.
 * suspect problem is accessing an empty listOfAngels via SharedStorage.
 */
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
	public String outGoingTextMsg;

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
	protected void onStart(){
		super.onStart();
	}
	public Intent mIntent = new Intent();
	@Override
	protected void onResume() {
		super.onResume();
		wind = this.getWindow();
		wind.addFlags(LayoutParams.FLAG_DISMISS_KEYGUARD);
		wind.addFlags(LayoutParams.FLAG_SHOW_WHEN_LOCKED);
		wind.addFlags(LayoutParams.FLAG_TURN_SCREEN_ON);
		mIntent = getIntent();
		try{
			if(mIntent !=null && mIntent.getExtras().getString("senderPhone").length()>1 ) {
				sendPhoneNumber = mIntent.getExtras().getString("senderPhone");
				msgReceived = mIntent.getExtras().getString("senderMsg");
				listOfAngels.get(hashtable.get(sendPhoneNumber)).setLastComment(msgReceived);
				//lAdapter.getItem(hashtable.get(sendPhoneNumber)).setLastComment(msgReceived);
				//Log.d(Constants.DeBugTAG, "Received : " + msgReceived + " to: " + sendPhoneNumber);
			}
		} catch (NullPointerException  e) { e.printStackTrace(); }
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
		
		//if(listOfAngels != null) if (!listOfAngels.isEmpty()) { addAngelsToThisView(); }
		
		if(sharedStorage.isAngelListCreated()) {
			listOfAngels = sharedStorage.getAngelList();
			addAngelsToThisView();
		}
	}
	
	private void addAngelsToThisView() {
		//adapter.add(new OneComment(true, "Hi Angels, do you know how to change a tire?!"));
		for (int x = 0; x < listOfAngels.size(); x++){
			Log.d(Constants.DeBugTAG, "MainDiscussion Resolves hash index : " + x + " to: " + listOfAngels.get(x).getPhoneNumber());
			lAdapter.add(listOfAngels.get(x));
			hashtable.put(listOfAngels.get(x).getPhoneNumber(), x);
		}
	}
   /* Method is not used. Button is made invisible.  
	*/
	public void setLocalBtnControls(){
		 sendBtn	  = (Button)   findViewById(R.id.quit);
		 myTextMsg	  = (TextView) findViewById(R.id.myTextMsg);
		 nine11Btn	  = (Button)   findViewById(R.id.about);
		 nine11Btn.setText("Call 911");
		 nine11Btn.setVisibility(View.GONE);
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
					setNativeKeyPadVisibility(false);
					initiatePopupWindow();

					for(int x=0;  x < listOfAngels.size(); x++ ) {
						Log.d(Constants.DeBugTAG, "SendButton string: " + listOfAngels.get(x).getPhoneNumber());
						smsSender.sendSmsByManager(listOfAngels.get(x).getPhoneNumber(), outGoingTextMsg);
					}
					myTextMsg.setText("");
				}
		 	});
	}
	private void setNativeKeyPadVisibility(boolean visible){
		InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE); 

		if(visible){
			imm.showSoftInput(myTextMsg, InputMethodManager.SHOW_IMPLICIT);
		}else{
			imm.hideSoftInputFromWindow(myTextMsg.getWindowToken(), 0);
		}
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
        	case R.id.angel_groupsettings:
        		Intent intent=new Intent(MainDiscussionActivity.this,AngelGroupSetupActivity.class);
        		startActivity(intent);
        		finish();
        		return true;
        	default:
        		return super.onOptionsItemSelected(item);
        }
    } 
}