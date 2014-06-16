package com.brighthalo.myangels;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

//import de.svenjacobs.loremipsum.LoremIpsum;

public class MainDiscussionActivity extends Activity {
	private DiscussArrayAdapter adapter;
	private ListView lv;
	private EditText editText1;
	private static Random random;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_discussion_activity);
		setBtnControls();
		random = new Random();

		lv = (ListView) findViewById(R.id.listView1);

		adapter = new DiscussArrayAdapter(getApplicationContext(), R.layout.listitem_discuss);

		lv.setAdapter(adapter);

		editText1 = (EditText) findViewById(R.id.editText1);
		editText1.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// If the event is a key-down event on the "enter" button
				if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
					// Perform action on key press
					adapter.add(new OneComment(false, editText1.getText().toString()));
					editText1.setText("");
					return true;
				}
				return false;
			}
		});

		addItems();
	}

	private void addItems() {
		adapter.add(new OneComment(true, "Hi Angels, lets talk"));
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
	public void setBtnControls(){
		  Button skipBtn, doneBtn;
		  TextView screenTitle;
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
}