package com.brighthalo.utilities;

import java.util.ArrayList;
import com.brighthalo.myangels.MyAngelConstants;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.gsm.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.brighthalo.myangels.MainDiscussionActivity;
import com.brighthalo.myangels.Splash;

public class SMSReceiver extends BroadcastReceiver{
	public ArrayList<String> numberList;  

	@Override
	public void onReceive(Context context, Intent intent) {
		numberList = intent.getStringArrayListExtra("GroupList");
		
		if(intent.getAction().equals("com.brighthalo.intent.action.TEST")){
			String name = intent.getStringExtra("AngelName");
			if (!name.isEmpty()) {
				Log.d(MyAngelConstants.DeBugTAG, "SMS Receiver got name: " + name);
			}

			if(!numberList.isEmpty()){
				for (int x=0; x<numberList.size(); x++)
					Log.d(MyAngelConstants.DeBugTAG, "SMS Receiver got list " + numberList.get(x));
			}
		}

		if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED"))  {
	        //---get the SMS message passed in---
	        Bundle bundle = intent.getExtras();        
	        SmsMessage[] msgs = null;
	        SmsMessage[] phoneNum = null;
	        
	        String str = ""; 
	        String phoneNUMBER = "";

	        if (bundle != null){
	            //---retrieve the SMS message received---
	            Object[] pdus = (Object[]) bundle.get("pdus");
	            msgs = new SmsMessage[pdus.length];            
	            for (int i=0; i<msgs.length; i++){
	                msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);                
	                str = msgs[i].getMessageBody().toString();       
	            }
	            
	            //---retrieve the SMS sender number ----
	            phoneNum = new SmsMessage[pdus.length];
	            for(int i = 0; i< phoneNum.length; i++){
	            	phoneNum[i]=SmsMessage.createFromPdu((byte[])pdus[i]);
	                phoneNUMBER += phoneNum[i].getOriginatingAddress();
	            }
	            
	            //---display the new SMS message---
	            if(numberList.contains(phoneNUMBER)){ // only show if on angel group list
	            	Intent i = new Intent(context, MainDiscussionActivity.class);
	            	i.putExtra("senderPhone", phoneNUMBER);
	            	i.putExtra("senderMsg", str.trim());
	            	i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					Log.d(MyAngelConstants.DeBugTAG, "SMS Receiver message: " + i.getAction());
					context.startActivity(i);
	            }
	        }//end if(bundle != null){}
	    }
	}
}