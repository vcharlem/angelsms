package com.brighthalo.myangels;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentProviderOperation;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.brighthalo.myangels.MyAngelConstants;

public class AngelGroupSetupActivity extends Activity {
	public final static String CONTACTNAMESPACE = "ContactsContract.CommonDataKinds";
	Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
	Uri queryUri = ContactsContract.Contacts.CONTENT_URI;
	public ArrayList<ContactsContract> listContacts = new ArrayList<ContactsContract>();
	
	String selection = ContactsContract.Contacts.DISPLAY_NAME + " IS NOT NULL";
	String[] projection = new String[] { ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME};		
	String[] from = {ContactsContract.Contacts.DISPLAY_NAME};
	ListView lvContacts, lvAngels;
	public Cursor cursor,	 cursor1, cursorAngels;
	public ArrayAdapter<String> angelAdapter;
	public TextView Angel1, Angel2, Angel3, Angel4;
	ImageButton btnclk;
	public ArrayList<String> listAngels = new ArrayList<String>();
	public ArrayList<ContactsContract> listAngelsContract = new ArrayList<ContactsContract>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.angelgroup_setup_screen);
		setBtnControls();
		
		String [] from_colmn = {
				ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, 
				ContactsContract.CommonDataKinds.Phone._ID
		};
		lvContacts = (ListView) findViewById(R.id.list);
		Angel1	   = (TextView) findViewById(R.id.nameAngel1);
		lvAngels   = (ListView) findViewById(R.id.listAngels);

		CursorLoader cursorLoader = new CursorLoader( this,  uri,  projection,  selection, null,  null);
		cursor = cursorLoader.loadInBackground();
		cursor1 = getContentResolver().query( uri, from_colmn, null, null,	null );
		startManagingCursor(cursor1);

		int [] to_item_view = { R.id.contactName};
		
		angelAdapter = new ArrayAdapter<String>(this, R.layout.mycontactangel, R.id.angeltext, listAngels);
        lvAngels.setAdapter(angelAdapter);

		SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(this, R.layout.mycontact, cursor1, from_colmn, to_item_view, 0);
	     ListAdapter adapter = new SimpleCursorAdapter(
	                this, R.layout.mycontact, 
	                cursor, from, to_item_view,  CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

	    lvContacts.setAdapter(adapter);        
		lvContacts.setOnItemClickListener(new OnItemClickListener() {
			
			  @Override
			  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				cursor.moveToPosition(position);
				int indexName = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);

				Log.d("myAngels---", " ******  Debug Triggered");
			    Toast.makeText(AngelGroupSetupActivity.this,
			      "Click-Click ListItem Number " + position, Toast.LENGTH_LONG).show();
			  }
			});	
		/*	//http://android-er.blogspot.com/2012/11/query-contacts-database-display-in.html */
	     }
	
	public void setBtnControls(){
		  Button skipBtn, doneBtn;
		  TextView screenTitle;
		  skipBtn     = (Button)   findViewById(R.id.button_skip);
		  doneBtn     = (Button)   findViewById(R.id.button_done);	
		  screenTitle = (TextView) findViewById(R.id.activity_title);
		  screenTitle.setText("Group");

	      skipBtn.setOnClickListener(new OnClickListener() {
	  		public void onClick(View v) {
	  			 Intent intent1 = new Intent(AngelGroupSetupActivity.this,MainDiscussionActivity.class);
	             startActivity(intent1);
	  		}
	  	  });
	      
	      doneBtn.setOnClickListener(new OnClickListener() {
	  		public void onClick(View v) {
	  			Intent intent1 = new Intent(AngelGroupSetupActivity.this,MainDiscussionActivity.class);
	             startActivity(intent1);
	             }
	  	  });
		}
	public void onActionButtonClick(View view) {
		
		cursor.moveToPosition(lvContacts.getPositionForView(view));
		
		int indexName = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
		int indexNumber = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

		String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
		if ((listAngels.size() < MyAngelConstants.MaxAngelGroup) && !(listAngels.contains(displayName)))  {
			listAngels.add(displayName);
		}
		//listAngelsContract.add(ContactsContract.Data.);
		
		Angel1.setText("");
		
		//Toast.makeText(AngelGroupSetupActivity.this,
		//	      "Click ListIte NAME  " + displayName , //lvContacts.getPositionForView(view), 
		//	      Toast.LENGTH_SHORT).show();
	}
	
	public void setListViewAttributes(){
		lvContacts.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    	lvContacts.setCacheColorHint(00000000);
        lvContacts.setSelector(R.drawable.greycellselected);

		lvContacts.setItemsCanFocus(true);
    	lvContacts.setClickable(true);
    	lvContacts.setFocusable(false);
    	lvContacts.setFocusableInTouchMode(false);
    	
    	lvAngels.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    	lvAngels.setCacheColorHint(00000000);
    	lvAngels.setSelector(R.drawable.greycellselected);

    	lvAngels.setItemsCanFocus(true);
    	lvAngels.setClickable(true);
    	lvAngels.setFocusable(false);
    	lvAngels.setFocusableInTouchMode(false);
	}
}
