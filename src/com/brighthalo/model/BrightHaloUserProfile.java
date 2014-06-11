/**
 * 
 */
package com.brighthalo.model;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;
import android.content.ContextWrapper.*;
/**
 * @author vlad
 */

public class BrightHaloUserProfile {
	public static final String PREFERENCE_USER = "UserLoginInfo";
	public static SharedPreferences mUserPreferences;
	protected static Context mContext;
	public JSONObject obj = null;
	public SharedPreferences.Editor mUserEditor = null;
	
	public BrightHaloUserProfile (Context context) {
		mContext = context;
		mUserPreferences = mContext.getSharedPreferences(PREFERENCE_USER, Context.MODE_PRIVATE);
		mUserEditor = mUserPreferences.edit();			
	}

	public String fetchMyUserId(){		
		mUserPreferences = mContext.getSharedPreferences(PREFERENCE_USER, Context.MODE_PRIVATE);
		String userid =  mUserPreferences.getString("id", "0");
		return userid;
	}
	public  void storeInfoFromSvr(String jsonData){
		mUserPreferences = mContext.getSharedPreferences(PREFERENCE_USER, Context.MODE_PRIVATE);
		SharedPreferences.Editor mUserEditor = mUserPreferences.edit();	

    	Log.i("Overwatch StrMgr", "JSON Content from SVR: " + jsonData);
        try { obj = new JSONObject(jsonData); } catch(JSONException e) { e.printStackTrace(); }
        
		try {
      	  Log.i("Overwatch"," SvrAPI Saving user ID from JSon:" + String.valueOf(obj.getInt("id")) );
		  mUserEditor.putString("id", String.valueOf(obj.getInt("id")));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(mUserEditor.commit()) {
	    	Toast.makeText(mContext,"Saved user id", Toast.LENGTH_LONG).show();
	   }
	}
	public void storeProfileInfo(String firstname, String lastname, String phonenum, String address, String disability){
	   //mUserPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
	   mUserPreferences = mContext.getSharedPreferences(PREFERENCE_USER, mContext.MODE_PRIVATE);
	   SharedPreferences.Editor mUserEditor = mUserPreferences.edit();
	   
	   mUserEditor.putString("firstname", firstname);
	   mUserEditor.putString("lastname",  lastname);
	   mUserEditor.putString("phonenum",  phonenum);
	   mUserEditor.putString("address",   address);
	   mUserEditor.putString("disability",disability);

	   if(mUserEditor.commit()) {
	    	Toast.makeText(mContext,"Saved Device Token Data", Toast.LENGTH_LONG).show();
	   }	
	}

	public void saveRxInfo(String rx1,  String rx2,  String rx3,  String rx4,
						   String dos1, String dos2, String dos3, String dos4){
		   //mUserPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
		   mUserPreferences = mContext.getSharedPreferences(PREFERENCE_USER, mContext.MODE_PRIVATE);
		   SharedPreferences.Editor mUserEditor = mUserPreferences.edit();
		   
		   mUserEditor.putString("rx1", rx1);
		   mUserEditor.putString("rx2", rx2);
		   mUserEditor.putString("rx3", rx3);
		   mUserEditor.putString("rx4", rx4);

		   mUserEditor.putString("dos1", dos1);
		   mUserEditor.putString("dos2", dos2);
		   mUserEditor.putString("dos3", dos3);
		   mUserEditor.putString("dos4", dos4);

		   if(mUserEditor.commit()) {
		    	Toast.makeText(mContext,"Saved Your Script Data", Toast.LENGTH_LONG).show();
		   }	
			
		}
	public void storeDeviceToken(String device_token) {
	 //TODO Auto-generated method stub
	   mUserPreferences = PreferenceManager.getDefaultSharedPreferences(mContext.getApplicationContext());
	 //prefs = this.getApplicationContext().getSharedPreferences(PREFERENCE_USER);
	   SharedPreferences.Editor mUserEditor = mUserPreferences.edit();
	   mUserEditor.putString("device_token", device_token);
	   if(mUserEditor.commit()) {
	    	Toast.makeText(mContext,"Saved Device Token Data", Toast.LENGTH_LONG).show();
	   }	
	}
}
