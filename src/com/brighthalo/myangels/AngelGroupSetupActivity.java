package com.brighthalo.myangels;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.Toast;

public class AngelGroupSetupActivity extends Activity {
	public final static String CONTACTNAMESPACE = "ContactsContract.CommonDataKinds";
	public ArrayList<ContactsContract> listContacts = new ArrayList<ContactsContract>();
	ListView lvContacts;
	Cursor 	 cursor1;
	ImageButton btnclk;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.angelgroup_setup_screen);
	//  btnclk = (ImageButton) findViewById(R.id.btnAction);
	  //btnclk.setFocusable(true);
		lvContacts = (ListView) findViewById(R.id.listContacts);

		//CursorLoader cursorLoader = new CursorLoader( this,  queryUri,  projection,  selection, null,  null);

		cursor1 = getContentResolver().query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
				null, 													// No filter, return all columns
				null, //ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER + " = 1" , 	// selection,
				null, 													// selectionArgs,
				ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" ASC"   		// Sorting Order Order
			);
		startManagingCursor(cursor1);
		
		String [] from_colmn = {
				ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, 
				ContactsContract.CommonDataKinds.Phone._ID
		};

		int [] to_item_view = { R.id.contactName};
	
		SimpleCursorAdapter listAdapter = 
			new SimpleCursorAdapter(this, R.layout.mycontact, cursor1, from_colmn, to_item_view, 0);
		
    	lvContacts.setClickable(false);
    	lvContacts.setFocusableInTouchMode(false);
    	lvContacts.setFocusable(false);
		lvContacts.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    	lvContacts.setCacheColorHint(00000000);
        lvContacts.setSelector(R.drawable.greycellselected);
		
		lvContacts.setItemsCanFocus(false);		
        lvContacts.setAdapter(listAdapter);
        
		lvContacts.setOnItemClickListener(new OnItemClickListener() {
			
			  @Override
			  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Log.d("myAngels---", " ******  Debug Triggered");
			    Toast.makeText(AngelGroupSetupActivity.this,
			      "Click-Click ListItem Number " + position, Toast.LENGTH_LONG).show();
			  }
			  
			/*  @Override
			  public void onListItemClick(ListView l, View view, int position, long id){
				  super.onListItemClick(l,view,position,id);
				  Toast.makeText(AngelGroupSetupActivity.this,
					      "Click-Click ListItem Number " + pos, Toast.LENGTH_LONG).show()
			  }*/
			});	
		/*			
		//http://android-er.blogspot.com/2012/11/query-contacts-database-display-in.html
	/*	Uri queryUri = ContactsContract.Contacts.CONTENT_URI;
		
		String[] projection = new String[] { ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME};		
		String selection = ContactsContract.Contacts.DISPLAY_NAME + " IS NOT NULL";
		Cursor cursor = cursorLoader.loadInBackground();
		String[] from = {ContactsContract.Contacts.DISPLAY_NAME};
		
	     ListAdapter adapter = new SimpleCursorAdapter(
	                this, android.R.layout.simple_list_item_1, 
	                cursor, from, to,  CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
	  */
	     }
	
	public void onActionButtonClick(View view) {
	    Toast.makeText(AngelGroupSetupActivity.this,
			      "Click ListItem Number " + lvContacts.getPositionForView(view), 
			      Toast.LENGTH_LONG).show();
	}
}
