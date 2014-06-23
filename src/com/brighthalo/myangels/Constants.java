package com.brighthalo.myangels;

import android.net.Uri;
import android.provider.ContactsContract;

public final class Constants {
	public final static int MaxAngelGroup = 5;
	public final static int MinAngelGroup = 1;
	public final static String DeBugTAG = "MyAngel Debug";
	public final static String CONTACTNAMESPACE = "ContactsContract.CommonDataKinds";

	public final static String SMS_INTENT = "com.brighthalo.action.intent.INCOMING_SMS_MSG";
	public final static String SMS_TEST = "com.brighthalo.intent.action.TEST";

	public final static String baseUri  = "ContactsContract.Contacts";
	public final static String baseUriCommon = "ContactsContract.CommonDataKinds.Phone";
	
	public final static String DISPLAY_NAME = "ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME";
	public final static String NUMBER 		= "ContactsContract.CommonDataKinds.Phone.NUMBER";
	
	public final static Uri BASE_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
	

	private String[] from = new String []{
			Constants.baseUriCommon + "._ID", 
			Constants.baseUriCommon + ".DISPLAY_NAME",
			Constants.baseUriCommon + ".PHOTO_ID"
	};
}
