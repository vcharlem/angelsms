package com.brighthalo.myangels;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class BtnControls extends Activity{
	public Context mContext;

	public BtnControls(Context mContext) {
		this.mContext = mContext;
	}


	public void setLocalBtnControls(final Context mContext){
		  Button skipBtn, doneBtn;
		  TextView screenTitle;
		  skipBtn     = (Button)   findViewById(R.id.button_skip);
		  doneBtn     = (Button)   findViewById(R.id.button_done);	
		  screenTitle = (TextView) findViewById(R.id.activity_title);
		  screenTitle.setText("Chat");

	      skipBtn.setOnClickListener(new OnClickListener() {
	  		public void onClick(View v) {
	  			 Intent intent1 = new Intent(mContext,Splash.class);
	             startActivity(intent1);
	  		}
	  	  });
	      
	      doneBtn.setOnClickListener(new OnClickListener() {
	  		public void onClick(View v) {
	  			 Intent intent1 = new Intent(mContext,Splash.class);
	             startActivity(intent1);
	  		}
	  	  });
	}
	
	
	public void setGlobalBtnControls(){
		  Button skipBtn, doneBtn;
		  TextView screenTitle;
		  skipBtn     = (Button)   findViewById(R.id.button_skip);
		  skipBtn.setVisibility(View.GONE);
		  doneBtn     = (Button)   findViewById(R.id.button_done);	
		  doneBtn.setVisibility(View.GONE);
		  
		  screenTitle = (TextView) findViewById(R.id.activity_title);
		  screenTitle.setText("Select Your Angels");

	      skipBtn.setOnClickListener(new OnClickListener() {
	  		public void onClick(View v) {
	
	  		}
	  	  });
	      
	      doneBtn.setOnClickListener(new OnClickListener() {
	  		public void onClick(View v) {

	        }
	  	  });
		}
}
