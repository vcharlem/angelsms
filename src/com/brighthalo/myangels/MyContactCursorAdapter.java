package com.brighthalo.myangels;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MyContactCursorAdapter extends SimpleCursorAdapter{
	public Context mContext;
	public int mLayout;
	public Cursor mCursor;
	public String [] mFrom;
	public int [] mTo;
	public int mFlags;
	public View view;
	public LayoutInflater inflator;
	public MyContactCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
		super(context, layout, c, from, to, flags);
		this.mContext = context;		this.mLayout = layout;		this.mCursor = c;
		this.mTo = to;					this.mFrom = from;			this.mFlags = flags;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// TODO Auto-generated method stub
		super.bindView(view, context, cursor);
		int columnId 		= cursor.getPosition();
		int nameColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
		String displayName  = cursor.getString(nameColumnIndex);
		
		int numColumnIndex  = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
		String phoneNumber  = cursor.getString(numColumnIndex);
		
		int photoColmnIndex	= cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_ID);
		String photoUri		= cursor.getString(photoColmnIndex);

		TextView nameTView = (TextView) view.findViewById(R.id.contactName);
		TextView phoneNumTView = (TextView) view.findViewById(R.id.phoneNum);
		ImageView photoImageView = (ImageView) view.findViewById(R.id.contactimg);
		
    	Log.d(MyAngelConstants.DeBugTAG, "bindView position is: " + columnId);

		if(nameTView != null) 	  nameTView.setText(displayName);
		if(phoneNumTView != null) phoneNumTView.setText(phoneNumber);
		
		if(photoImageView !=null) {
			//photoImageView.setImageBitmap(loadContactPhoto(mContext.getContentResolver(),columnId));
			//photoImageView.setImageBitmap(getContactPhoto(columnId));
			//photoImageView.setImageBitmap(openPhoto(columnId));	
			photoImageView.setImageBitmap(queryContactImage(columnId));	
		}

		int position = cursor.getPosition();
	}
    @Override
    public View newView (Context context, Cursor cursor, ViewGroup parent) {
		Cursor c = mCursor; //getCursor();
	     Log.d(MyAngelConstants.DeBugTAG, "newView Cursor position is: " + c.getPosition());

		inflator = LayoutInflater.from(context);
		view = inflator.inflate(mLayout, parent, false);
		
		int indexCol = c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
		String displayName  = c.getString(indexCol);
		
		TextView nameTView = (TextView) view.findViewById(R.id.contactName);
		if (nameTView != null) nameTView.setText(displayName);

		return view;
    }
	@Override
	public ViewBinder getViewBinder() {
		// TODO Auto-generated method stub
		return super.getViewBinder();
	}	
	 public Bitmap openPhoto(long contactId) {
	     Uri contactUri = ContentUris.withAppendedId(Contacts.CONTENT_URI, contactId);
	     Uri photoUri = Uri.withAppendedPath(contactUri, Contacts.Photo.CONTENT_DIRECTORY);
	     
	     
	     Cursor cursor = mContext.getContentResolver().query(photoUri,
	          new String[] {Contacts.PHOTO_ID}, null, null, null);
	
	     Log.d(MyAngelConstants.DeBugTAG, "openPhoto Cursor position is: " + cursor.getPosition());
	     
	     
	     if (cursor == null) {
	         return null;
	     }
	     try {
	         if (cursor.moveToFirst()) {
	             byte[] data = cursor.getBlob(0);
	             if (data != null) {
	                 //return new ByteArrayInputStream(data);
	                 return BitmapFactory.decodeStream(new ByteArrayInputStream(data));
	             }
	         }
	     } finally {
	         cursor.close();
	     }
	     return null;
	 }
	private Bitmap queryContactImage(int imageDataRow) {
	    Cursor c = mContext.getContentResolver().query(ContactsContract.Data.CONTENT_URI, new String[] {
	        ContactsContract.CommonDataKinds.Photo.PHOTO
	    }, ContactsContract.Data._ID + "=?", new String[] {
	        Integer.toString(imageDataRow)
	    }, null);
	    
    	Log.d(MyAngelConstants.DeBugTAG, "queryContactImage Cursor position is: " + c.getPosition());

	    byte[] imageBytes = null;
	    if (c != null) {
	        if (c.moveToFirst()) {
	            imageBytes = c.getBlob(0);
	        }
	        c.close();
	    }
	
	    if (imageBytes != null) {
	        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length); 
	    } else {
	        return null;
	    }
	}
	public Bitmap getContactPhoto(long contactID) {
		Uri photoUri = null;
		if(BitmapCache.getImage(contactID) == null){
			ContentResolver cr = mContext.getContentResolver();
			photoUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactID);
			if (photoUri != null) {
				InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(
						cr, photoUri);
				if (input != null) {
					BitmapCache.putImage(contactID,BitmapFactory.decodeStream(input) );
					return BitmapCache.getImage(contactID);
				}
			} else {
				Bitmap defaultPhoto = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.picimage);
				BitmapCache.putImage(contactID,defaultPhoto );
				return BitmapCache.getImage(contactID);
			}
		}else{
			return BitmapCache.getImage(contactID);
		}
		Bitmap defaultPhoto = BitmapFactory.decodeResource(mContext.getResources(),  R.drawable.picimage);
		return defaultPhoto;
	}
	Bitmap loadContactPhoto (ContentResolver cr, long id) {
		Uri uri = ContentUris.withAppendedId(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, id);
		InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(cr, uri);
		return BitmapFactory.decodeStream(input);
	}
}
