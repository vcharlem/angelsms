package com.brighthalo.myangels;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.brighthalo.myangels.SharedStorage;
import com.brighthalo.myangels.BtnControls;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class InstructionActivity extends Activity {
	public BtnControls btnControls = new BtnControls(InstructionActivity.this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.instruction_screen);	
		setLocalBtnControls();
		setGlobalBtnControls();
	}
	public void setGlobalBtnControls(){
		  Button skipBtn, doneBtn;
		  TextView screenTitle;
		  skipBtn = (Button) findViewById(R.id.button_skip);
		  doneBtn = (Button) findViewById(R.id.button_done);	
		  doneBtn.setVisibility(View.GONE);
		  skipBtn.setVisibility(View.GONE);

		  screenTitle = (TextView) findViewById(R.id.activity_title);
		  screenTitle.setText("Welcome to MyAngels");
	}
	public void setLocalBtnControls(){
		  Button doneNextBtn = (Button) findViewById(R.id.donenext);

	      doneNextBtn.setOnClickListener(new OnClickListener() {
	  		public void onClick(View v) {
	  			SharedStorage sharedStorage = new SharedStorage(InstructionActivity.this);
	  			sharedStorage.setInstructionSeen();

	  			Intent intent1 = new Intent(InstructionActivity.this,AngelGroupSetupActivity.class);
	            startActivity(intent1);
	  		}
	  	  });
	}
}
