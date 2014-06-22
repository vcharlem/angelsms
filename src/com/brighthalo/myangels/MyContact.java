package com.brighthalo.myangels;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;

public class MyContact {//implements Serializable{
	public String name;
	public String phoneNumber;
	public Bitmap contactPhoto;
	public int myContactId;
	
	public MyContact (String name, String phoneNumber, Bitmap contactPhoto, int myContactId) {
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.contactPhoto = contactPhoto;
		this.myContactId = myContactId;
	}
	public MyContact(JSONObject object) {	// To Angel from server using JSON Objects
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
	
}
