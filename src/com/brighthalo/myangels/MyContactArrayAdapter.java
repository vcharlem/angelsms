package com.brighthalo.myangels;

import java.io.InputStream;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MyContactArrayAdapter extends ArrayAdapter<MyContact>{
	private Context context;
	public MyContact p;
	public MyContactArrayAdapter(Context context, int resource,
			int textViewResourceId, List<MyContact> objects) {
		super(context, resource, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
	}

	public MyContactArrayAdapter(Context context, int resource,
			int textViewResourceId, MyContact[] objects) {
		super(context, resource, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
	}

	public MyContactArrayAdapter(Context context, int textViewResourceId, List<MyContact> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//return super.getView(position, convertView, parent);
    	Log.d(Constants.DeBugTAG, "position number is: " + position);
    	
		View v =  convertView;
    	
		p = getItem(position);

		if (v == null){
			LayoutInflater vi = LayoutInflater.from(getContext());
			v = vi.inflate(R.layout.mycontactangel, null);
		}
		
		if (p != null) {
			TextView name = (TextView) v.findViewById(R.id.contactName);
			
			ImageButton btnAction = (ImageButton) v;
			btnAction.setBackgroundResource(R.drawable.callon);
			
			try {
				((ImageView)v).setImageBitmap(getContactPhoto(1));
			}catch(Exception e){
				e.printStackTrace();
			}
		}	
		return v;
	}

	public Bitmap getContactPhoto(long contactID) {

		Uri photoUri = null;
		if(BitmapCache.getImage(contactID) == null){
			ContentResolver cr = context.getContentResolver();
			photoUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactID);
			if (photoUri != null) {
				InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(
						cr, photoUri);
				if (input != null) {
					BitmapCache.putImage(contactID,BitmapFactory.decodeStream(input) );
					return BitmapCache.getImage(contactID);
				}
			} else {
				Bitmap defaultPhoto = BitmapFactory.decodeResource(context.getResources(), R.drawable.face54x55);
				BitmapCache.putImage(contactID,defaultPhoto );
				return BitmapCache.getImage(contactID);
			}
			
		}else{
			return BitmapCache.getImage(contactID);
		}
		Bitmap defaultPhoto = BitmapFactory.decodeResource(context.getResources(),  R.drawable.face54x55);
		return defaultPhoto;
	}
	Bitmap loadContactPhoto (ContentResolver cr, long id) {
		Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id);
		InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(cr, uri);
		
		return BitmapFactory.decodeStream(input);
	}
	
	
}
