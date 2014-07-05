package com.brighthalo.myangels;
import org.json.JSONException;
import org.json.JSONObject;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
public final class Angel implements Parcelable{
	public String name;
	public String phoneNumber;
	public Bitmap contactPhoto;
	public int contactId;
	public String lastComment;
	
	public Angel(JSONObject object) {	// To Angel from server using JSON Objects
		try {
			this.name = object.getString("name");
			this.phoneNumber = object.getString("phoneNumber");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	public Angel(String name, String phoneNumber, int contactId) {
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.contactId = contactId;
		this.lastComment = "";
	}

	public String getName() { return name; }
	public void setName(String name) {this.name = name; }
	public void setContactId(int contactId){ this.contactId = contactId; }
	public int getContactId(){ return contactId; }
	public String getPhoneNumber() {
		return phoneNumber; 
	}
	public String getLastComment() {
		return this.lastComment;
	}
	public void setLastComment(String lastComment) { 
		this.lastComment = lastComment;
		Log.d(Constants.DeBugTAG, "From Angel, last comment saved for Angel: "  + this.name + ":"+ this.lastComment);
	}
	
	public static String formatPhoneNumber(String phoneNumber){
		phoneNumber = phoneNumber.replaceAll("[^\\w\\s+]","").replaceAll("\\s+", "");
		if((phoneNumber.length() >=11))
				phoneNumber =phoneNumber.substring(1);
		Log.d(Constants.DeBugTAG, "Angel.class is reformating number to: " + phoneNumber);
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = formatPhoneNumber(phoneNumber);
	}
	public Bitmap getContactPhoto() { return contactPhoto; }
	public void setContactPhoto(Bitmap contactPhoto) {
		this.contactPhoto = contactPhoto;
	}
	public Angel(){} //constructor for Parcelable
//http://www.easyinfogeek.com/2014/01/android-tutorial-two-methods-of-passing.html
    public static final Parcelable.Creator<Angel> CREATOR = new Creator<Angel>() {  
	   public Angel createFromParcel(Parcel source) {  
	       Angel mAngel = new Angel();  
	       mAngel.name = source.readString();  
	       mAngel.phoneNumber = source.readString();  
	       mAngel.contactId = source.readInt();  
	       mAngel.lastComment = source.readString();
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
		dest.writeString(lastComment);
	}
}
