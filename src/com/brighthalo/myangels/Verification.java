package com.brighthalo.myangels;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
/* Verification module will be used to validate user number.
 * User registers -> Server replies with Verification Code
 * User confirms reception of verification Code
 */
public class Verification extends Activity{
	Button skipBtn, doneBtn;
	EditText phoneNum, passwordEditText, verificatn;
	private final static boolean SHOW_NATIVE_KEYPAD2 = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
	  // TODO Auto-generated method stub
	  super.onCreate(savedInstanceState);
	
	  setContentView(R.layout.verification_screen);
	  verificatn = (EditText) findViewById(R.id.verify_code);
	  phoneNum   = (EditText) findViewById(R.id.phoneNum);
	  
	  //setNativeKeyPadVisibility();
	  setBtnControls();
	}
	
	public void setBtnControls(){
		  Button skipBtn, doneBtn;
		  TextView screenTitle;
		  skipBtn     = (Button)   findViewById(R.id.button_skip);
		  doneBtn     = (Button)   findViewById(R.id.button_done);	
		  screenTitle = (TextView) findViewById(R.id.activity_title);
		  screenTitle.setText("Verification");

	      skipBtn.setOnClickListener(new OnClickListener() {
	  		public void onClick(View v) {
	  			 Intent intent1 = new Intent(Verification.this,AngelGroupSetupActivity.class);
	             startActivity(intent1);
	  		}
	  	  });
	      
	      doneBtn.setOnClickListener(new OnClickListener() {
	  		public void onClick(View v) {
	  			Intent intent1 = new Intent(Verification.this,MainDiscussionActivity.class);
	             startActivity(intent1);
	             }
	  	  });
		}
	
	private void setNativeKeyPadVisibility(){
		InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE); 

		if(SHOW_NATIVE_KEYPAD2){
			imm.showSoftInput(phoneNum, InputMethodManager.SHOW_IMPLICIT);
		}else{
			imm.hideSoftInputFromWindow(phoneNum.getWindowToken(), 0);
		}
	}
}
