package com.brighthalo.myangels;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public final class SelectedAngelArrayAdapter extends ArrayAdapter<Angel> {
	private ArrayList<Angel> listOfAngels = new ArrayList<Angel>();
	public Context mContext;
	public TextView angeltext;
	private ImageView contactPhoto;
	private ImageButton btnAction;
	public SelectedAngelArrayAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		this.mContext = context;
		// TODO Auto-generated constructor stub
	}
	@Override
	public void add(Angel object) {
		listOfAngels.add(object);
		super.add(object);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.mycontactangel, parent, false);
		}
		Angel angel = getItem(position);
		angeltext = (TextView) row.findViewById(R.id.angeltext); 
		angeltext.setText(angel.getName());

		btnAction = (ImageButton) row.findViewById(R.id.btnAction);
		btnAction.setVisibility(View.GONE);

		contactPhoto = (ImageView) row.findViewById(R.id.angelimg); 

		try{
		  //contactPhoto.setImageBitmap(getContactPhoto( angel.getContactId()) );
			contactPhoto.setImageBitmap(BitmapCache.getImage((long) angel.getContactId()));
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return row;
	}
}