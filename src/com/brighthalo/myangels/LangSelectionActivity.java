package com.brighthalo.myangels;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
/*
 */
public class LangSelectionActivity extends Activity{
	Button skipBtn, doneBtn;
	TextView screenTitle;
	private final static boolean SHOW_NATIVE_KEYPAD = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
	  // TODO Auto-generated method stub
	  super.onCreate(savedInstanceState);

	  setContentView(R.layout.lang_selection_activty);
	  skipBtn     = (Button)   findViewById(R.id.button_skip);
	  doneBtn     = (Button)   findViewById(R.id.button_done);	
	  screenTitle = (TextView) findViewById(R.id.activity_title);
	  screenTitle.setText("Language");
	  setBtnListeners();
	}
	
	
	   /* 'Done Btn' - will close this activity for now. 
	    * 'Skip Btn' - move to select angel group
	    * 'Quit Btn' - close application
	    */	
		private void setBtnListeners(){
	      skipBtn.setOnClickListener(new OnClickListener() {
	  		public void onClick(View v) {
	  			 Intent intent1 = new Intent(LangSelectionActivity.this,Registration.class);
	             startActivity(intent1);
	  		}
	  	  });
	      
	      doneBtn.setOnClickListener(new OnClickListener() {
	  		public void onClick(View v) {
	  			 Intent intent1 = new Intent(LangSelectionActivity.this,Registration.class);
	             startActivity(intent1);
	  			 //finish();
	  		}
	  	  });
		}
}