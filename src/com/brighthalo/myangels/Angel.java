package com.brighthalo.myangels;

import org.json.JSONException;
import org.json.JSONObject;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public final class Angel implements Parcelable{
	public String name;
	public String phoneNumber;
	public Bitmap contactPhoto;
	public int contactId;
	
	public Angel (String name, String phoneNumber, int contactId) {
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.contactId = contactId;
	}
	public Angel () {
		
	}
	
	public Angel(JSONObject object) {	// To Angel from server using JSON Objects
		try {
			this.name = object.getString("name");
			this.phoneNumber = object.getString("phoneNumber");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Bitmap getContactPhoto() {
		return contactPhoto;
	}
	public void setContactPhoto(Bitmap contactPhoto) {
		this.contactPhoto = contactPhoto;
	}

	
//http://www.easyinfogeek.com/2014/01/android-tutorial-two-methods-of-passing.html
   public static final Parcelable.Creator<Angel> CREATOR = new Creator<Angel>() {  
	   public Angel createFromParcel(Parcel source) {  
	       Angel mAngel = new Angel();  
	       mAngel.name = source.readString();  
	       mAngel.phoneNumber = source.readString();  
	       mAngel.contactId = source.readInt();  
	       return mAngel;  
	   }  
	   public Angel[] newArray(int size) {  
	       return new Angel[size];  
	   }  
	 }; 
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(name);
		dest.writeString(phoneNumber);
		dest.writeInt(contactId);
	}
}
