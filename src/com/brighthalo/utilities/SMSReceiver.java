package com.brighthalo.utilities;

import java.util.ArrayList;
import java.util.Arrays;

import com.brighthalo.myangels.Constants;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.gsm.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.brighthalo.myangels.Angel;
import com.brighthalo.myangels.MainDiscussionActivity;
import com.brighthalo.myangels.SharedStorage;
import com.brighthalo.myangels.Splash;
/* Should remove test intent logic since it is no longer used
 */
public class SMSReceiver extends BroadcastReceiver{
	public static ArrayList<String> numberList;  
	public SharedStorage sharedStorage;
	public String tmpList;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		sharedStorage = new SharedStorage(context);
		tmpList = sharedStorage.getAngelGroup();
		numberList = new ArrayList<String>(Arrays.asList(tmpList.split(",")));

		if(intent.getAction().equals("com.brighthalo.intent.action.TEST")){
			String name = intent.getStringExtra("AngelName");
			if(!name.isEmpty()) {
				Log.d(Constants.DeBugTAG, "SMS Receiver got name: " + name);
			}
			if(!numberList.isEmpty()){
				for (int x=0; x<numberList.size(); x++)
					Log.d(Constants.DeBugTAG, "SMS Receiver got list " + numberList.get(x));
			}
		}

		if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED"))  {
			if(!numberList.isEmpty()){
				for (int x=0; x<numberList.size(); x++)
					Log.d(Constants.DeBugTAG, "From SMSReceiver SharedStorage returns: " + numberList.get(x));
			}		

	        //---get the SMS message passed in---
	        Bundle bundle = intent.getExtras();        
	        SmsMessage[] msgs = null;
	        SmsMessage[] phoneNum = null;
	        
	        String str = ""; String phoneNUMBER = "";

			sharedStorage = new SharedStorage(context);
			tmpList = sharedStorage.getAngelGroup();
			numberList = new ArrayList<String>(Arrays.asList(tmpList.split(",")));

			Log.d(Constants.DeBugTAG, "SharedStorage returns raw number: " + tmpList);

			if(!numberList.isEmpty())
				for (int x=0; x<numberList.size(); x++){
					numberList.set(x, Angel.formatPhoneNumber(numberList.get(x)));
			}	
			
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
		            	i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		            	i.setAction(Constants.SMS_INTENT);
		            	i.putExtra("senderPhone", phoneNUMBER);
		            	i.putExtra("senderMsg", str.trim());
						context.startActivity(i);
		           }
	            
	        }//end if(bundle != null){}
	    }
	}
}