package com.brighthalo.myangels;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AngelDiscussArrayAdapter extends ArrayAdapter<Angel> {
	private TextView countryName;
	public TextView angelName;
	private List<OneComment> countries = new ArrayList<OneComment>();
	private ArrayList<Angel> listOfAngels = new ArrayList<Angel>();
	private RelativeLayout wrapper;
	public  ImageView contactPhoto;
	public  Context mContext;
	@Override
	public void add(Angel object) {
		listOfAngels.add(object);
		super.add(object);
	}

	public AngelDiscussArrayAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		this.mContext = context;
		//this.listOfAngels = listOfAngels;
	}

	public int getCount() {
		return this.listOfAngels.size();
	}

	public Angel getItem(int index) {
	//	return this.countries.get(index);
		return this.listOfAngels.get(index);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.listitem_discuss, parent, false);
		}

		wrapper = (RelativeLayout) row.findViewById(R.id.wrapper);
		Angel coment = getItem(position);
		countryName = (TextView) row.findViewById(R.id.comment); 
		//countryName.setText(coment.comment);
		countryName.setText(listOfAngels.get(position).getName());
		countryName.setBackgroundResource(R.drawable.bubble_yellow);
		wrapper.setGravity(Gravity.LEFT);
		Angel angel =  listOfAngels.get(position);
		contactPhoto = (ImageView) row.findViewById(R.id.angelimg); 
		angelName = (TextView) row.findViewById(R.id.angelname);
		angelName.setText(angel.getName() + " wrote:");

		try{

			//contactPhoto.setImageBitmap(getContactPhoto( angel.getContactId()) );
			contactPhoto.setImageBitmap(BitmapCache.getImage((long) angel.getContactId()));
			Log.d(Constants.DeBugTAG, ":::" + angel.getName() + " ::: " + angel.getContactId() );

		}
		catch(Exception e){
			e.printStackTrace();
			Log.d(Constants.DeBugTAG, ":::" + angel.getName() + " ::: " + angel.getContactId() );
		}
		return row;
	}

}