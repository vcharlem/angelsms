package com.brighthalo.myangels;

import android.R.string;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
/*
 * 
 */
public class Registration extends Activity{
	Button skipBtn, doneBtn;
	EditText phoneNum, passwordEditText;
	private final static boolean SHOW_NATIVE_KEYPAD = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
	  // TODO Auto-generated method stub
	  super.onCreate(savedInstanceState);
	
	  setContentView(R.layout.register_screen);
	  skipBtn  = (Button)   findViewById(R.id.button_skip);
	  doneBtn  = (Button)   findViewById(R.id.button_done);
	  phoneNum = (EditText) findViewById(R.id.phoneNum);

	  //setNativeKeyPadVisibility();
	  setBtnListeners();
	}
	
   /* 'Done Btn' - will close this activity for now. 
    * 'Skip Btn' - move to select angel group
    * 'Quit Btn' - close application
    */	
	private void setBtnListeners(){
      skipBtn.setOnClickListener(new OnClickListener() {
  		public void onClick(View v) {
  			 Intent intent1 = new Intent(Registration.this,Verification.class);
             startActivity(intent1);
  		}
  	  });
      
      doneBtn.setOnClickListener(new OnClickListener() {
  		public void onClick(View v) {
  			 finish();
  		}
  	  });
	}
	
	private void setNativeKeyPadVisibility(){
		InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE); 
		if(SHOW_NATIVE_KEYPAD){
			imm.showSoftInput(phoneNum, InputMethodManager.SHOW_IMPLICIT);
		}else{
			imm.hideSoftInputFromWindow(phoneNum.getWindowToken(), 0);
		}
	}
}