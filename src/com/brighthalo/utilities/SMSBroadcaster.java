package com.brighthalo.utilities;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.telephony.SmsManager;
import android.widget.Toast;

public class SMSBroadcaster {
	public Context mContext;
	public SMSBroadcaster(Context context){
		mContext = context;
	}

	
    public void sendSmsByManager(String phoneNumber, String smsBody) {
    	try{
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, smsBody, null, null);
        } catch (Exception ex) {
            Toast.makeText(mContext,"Your sms has failed...", Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }
    public void sendSmsBySIntent(String phoneNumber, String smsBody) {
        // add the phone number in the data
        Uri uri = Uri.parse("smsto:" + phoneNumber);
        Intent smsSIntent = new Intent(Intent.ACTION_SENDTO, uri);
        // add the message at the sms_body extra field
        smsSIntent.putExtra("sms_body", smsBody);
        try{
            mContext.startActivity(smsSIntent);
        } catch (Exception ex) {
            Toast.makeText(mContext, "Your sms has failed...", Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }
    public void sendSmsByVIntent(String phoneNumber, String smsBody) {
        Intent smsVIntent = new Intent(Intent.ACTION_VIEW);
        // prompts only sms-mms clients
        smsVIntent.setType("vnd.android-dir/mms-sms");
        // extra fields for number and message respectively
        smsVIntent.putExtra("address", phoneNumber);
        smsVIntent.putExtra("sms_body", smsBody);
        try{
            mContext.startActivity(smsVIntent);
        } catch (Exception ex) {
            Toast.makeText(mContext, "Your sms has failed...", Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }
}
//http://androidthon.info/how-to-send-sms-in-android-built-in/
//http://www.compiletimeerror.com/2013/10/smsmessage-delivery-in-android-with.html#.U7c3BfldU5k
