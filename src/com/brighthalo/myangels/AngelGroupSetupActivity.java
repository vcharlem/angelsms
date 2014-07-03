package com.brighthalo.myangels;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ListView;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.brighthalo.myangels.Constants;
import com.brighthalo.myangels.MyContact;

public class AngelGroupSetupActivity extends Activity {
	public ArrayList<ContactsContract> listAngelsContract = new ArrayList<ContactsContract>();
	public ArrayList<ContactsContract> listContacts = new ArrayList<ContactsContract>();
	public ArrayList<MyContact> listOfContacts = new ArrayList<MyContact>();
	public ArrayList<Angel> listOfAngels= new ArrayList<Angel>();
	public ArrayList<String> listAngels = new ArrayList<String>();
	public ArrayList<String> listAngelsPNum = new ArrayList<String>();
	public ArrayAdapter<String> angelAdapter;
	public SelectedAngelArrayAdapter angAdapter;
	public MyContact myContact;
	public SharedStorage sharedStorage;
	public String phoneNumber, displayName;
	public CursorLoader cursorLoader;

	ListView lvContacts, lvAngels;
	public Cursor cursor,	 cursor1, cursorAngels;
	public TextView Angel1, Angel2, Angel3, Angel4;
	public LinearLayout linearLayout;
	ImageButton btnclk;
	public Button setGroupListBtn, clearGroupListBtn;
	public String [] from_colmn = {
			ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, 
			ContactsContract.CommonDataKinds.Phone._ID,
			ContactsContract.CommonDataKinds.Phone.NUMBER
	};
	public String[] projection = new String[] { 
			ContactsContract.CommonDataKinds.Phone._ID, 
			ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
			ContactsContract.CommonDataKinds.Phone.NUMBER,
			ContactsContract.CommonDataKinds.Phone.PHOTO_ID
	};	
	public String[] projection2 = new String[] { 
			ContactsContract.Contacts._ID, 
			ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
			ContactsContract.CommonDataKinds.Phone.NUMBER,
			ContactsContract.CommonDataKinds.Phone.PHOTO_ID
	};	
	Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
	Uri queryUri = ContactsContract.Contacts.CONTENT_URI;

	public String[] from = new String [] {
			ContactsContract.CommonDataKinds.Phone._ID, 
			Constants.DISPLAY_NAME,
			Constants.NUMBER,
			ContactsContract.CommonDataKinds.Phone.PHOTO_ID};		
	public int [] to_item_view = { R.id.contactName, R.id.contactimg };
	public String selection = ContactsContract.Contacts.DISPLAY_NAME + " IS NOT NULL";
	public boolean contactPhotoAvailable;
	public WindowManager windowManager;

	@Override
	protected void onCreate(Bundle savedInstanceState){
        windowManager = (WindowManager)getSystemService(Context.WINDOW_SERVICE);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.angelgroup_setup_screen);
		linearLayout = (LinearLayout) findViewById(R.id.ll3);
		lvContacts = (ListView) findViewById(R.id.list);
		Angel1	   = (TextView) findViewById(R.id.nameAngel1);
		lvAngels   = (ListView) findViewById(R.id.listAngels);

		setGlobalBtnControls();
		setLocalBtnControls();

		//ContactsContract.Contacts.CONTENT_URI this URI is for 2.0+ version
		cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null,
				 ContactsContract.Contacts.HAS_PHONE_NUMBER + " = 1" ,null, 
				 ContactsContract.Contacts.DISPLAY_NAME+" ASC");

		startManagingCursor(cursor);
		
		String[] columns = new String[] { 
				ContactsContract.Contacts.DISPLAY_NAME, 
				ContactsContract.Contacts._ID,
				ContactsContract.Contacts._ID};
		int[] names = new int[] { R.id.contactName , R.id.btnAction,R.id.contactimg };
		@SuppressWarnings("deprecation")
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.mycontact, cursor, columns, names);
		
		adapter.setViewBinder(new ViewBinder() {
			@Override
			public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
				switch (view.getId()) {
				case R.id.contactName:
					((TextView)view).setText(cursor.getString(columnIndex));
					break;
				case R.id.btnAction:
					ImageButton btnAction = (ImageButton) view;
					String displayName = cursor.getString(cursor.getColumnIndex(
											ContactsContract.Contacts.DISPLAY_NAME));
						btnAction.setBackgroundResource(R.drawable.callon);
						btnAction.setTag("C"+cursor.getString(columnIndex));
						btnAction.setVisibility(View.GONE);
					break;
				case R.id.contactimg:
					try{
						Bitmap tempPhoto = getContactPhoto(Long.parseLong(cursor.getString(columnIndex)));
						if(contactPhotoAvailable)
							((ImageView)view).setImageBitmap(tempPhoto);
						else
							((ImageView)view).setVisibility(View.GONE);
					}
					catch(Exception e){
						e.printStackTrace();
					}
					break;
				default:
					break;
				}
				return true;
			}
		});
	    lvContacts.setAdapter(adapter);
	    
	    adjustListViewWidthSize("fullscreen");
	    angelAdapter = new ArrayAdapter<String>(this, R.layout.mycontactangel, R.id.angeltext, listAngels);
		angAdapter = new SelectedAngelArrayAdapter(this, R.layout.mycontactangel);
        lvAngels.setAdapter(angAdapter);
    }
	
	public enum ScreenWidth {fullscreen, halfscreen};
	public void adjustListViewWidthSize(String widthSize){
        int width = windowManager.getDefaultDisplay().getWidth();
		ViewGroup.LayoutParams catman = lvContacts.getLayoutParams();
		ViewGroup.LayoutParams catman2 = lvContacts.getLayoutParams();
		ScreenWidth widthsize = ScreenWidth.valueOf(widthSize); 

		switch(widthsize){
			case fullscreen: width = (int) (width * 0.9);		
						 	 lvAngels.setVisibility(View.GONE);
				break;
			case halfscreen: width = width /2;
			 			 	 lvAngels.setVisibility(View.VISIBLE);
				break;
			default    : ;
		}
		catman.width = width;
		catman2.width = width;

		lvContacts.setLayoutParams(catman);
		lvAngels.setLayoutParams(catman2);
	}
	public String getContactNumber (String contactId) {
  	   Cursor cursorPhone = getContentResolver().query(
			  ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
              new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
              ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                        ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,
              new String[]{contactId},
              null);
  	   
        if (cursorPhone.moveToFirst()) {
            phoneNumber = cursorPhone.getString(cursorPhone.
            		getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        }		 
        cursorPhone.close();
		return phoneNumber;
	}
	
	public void onActionButtonClick(View view) {
		adjustListViewWidthSize("halfscreen");

		cursor.moveToPosition(lvContacts.getPositionForView(view));
		try {
				String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
				int contactID	= cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));
		    	Log.d(Constants.DeBugTAG, "contactId number is: " + contactId);

				phoneNumber = getContactNumber(contactId);
				displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
		    	Log.d(Constants.DeBugTAG, "Phone number is: " + phoneNumber);
		    	
				if ((listAngels.size() < Constants.MaxAngelGroup) && !(listAngels.contains(displayName)))  {
					listAngels.add(displayName);
					listAngelsPNum.add(Angel.formatPhoneNumber(phoneNumber) );
					angAdapter.add(new Angel(displayName,Angel.formatPhoneNumber(phoneNumber) , contactID));
					listOfAngels.add(new Angel(displayName,Angel.formatPhoneNumber(phoneNumber) , contactID));
				}
	    } catch(Exception e){
	    		System.out.println(e.getMessage());
	    }

		Angel1.setText("");
		
		//Toast.makeText(AngelGroupSetupActivity.this,
		//	      "Click ListIte NAME  " + displayName , //lvContacts.getPositionForView(view), 
		//	      Toast.LENGTH_SHORT).show();
	}
	Bitmap loadContactPhoto (ContentResolver cr, long id) {
		Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id);
		InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(cr, uri);
		
		return BitmapFactory.decodeStream(input);
	}
	public Bitmap getContactPhoto(long contactID) {
		Uri photoUri = null;
		if(BitmapCache.getImage(contactID) == null){
			ContentResolver cr = this.getContentResolver();
			photoUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactID);
			if (photoUri != null) {
				contactPhotoAvailable = true;
				InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(cr, photoUri);
				if (input != null) {
					BitmapCache.putImage(contactID,BitmapFactory.decodeStream(input) );
					return BitmapCache.getImage(contactID);
				}
			} else {
				Bitmap defaultPhoto = BitmapFactory.decodeResource(getResources(), R.drawable.picimage);
				BitmapCache.putImage(contactID,defaultPhoto );
				return BitmapCache.getImage(contactID);
			}
		}else{
			return BitmapCache.getImage(contactID);
		}
		Bitmap defaultPhoto = BitmapFactory.decodeResource(getResources(),  R.drawable.picimage);
		return defaultPhoto;
	}

	public void setLocalBtnControls(){
		setGroupListBtn   = (Button) findViewById(R.id.about);
		clearGroupListBtn = (Button) findViewById(R.id.quit);
		setGroupListBtn.setText("Save List");
		clearGroupListBtn.setText("Clear List");
		sharedStorage = new SharedStorage(AngelGroupSetupActivity.this);
		
		setGroupListBtn.setOnClickListener(new OnClickListener() {
			String CUSTOM_INTENT = "com.brighthalo.intent.action.TEST";

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setAction(CUSTOM_INTENT);
				i.putExtra("AngelName", "Bright Halo");
				i.putStringArrayListExtra("GroupList", listAngelsPNum);
				//sendBroadcast(i);
				sharedStorage.setAngelGroup(listAngelsPNum);
				sharedStorage.setAngelList(listOfAngels);
				//listOfAngels = sharedStorage.getAngelList();
				//listOfAngels.clear();
				
	  			Intent intent1 = new Intent(AngelGroupSetupActivity.this,MainDiscussionActivity.class);
	  			intent1.putParcelableArrayListExtra("listOfAngels", listOfAngels);
	            startActivity(intent1);
			}
		});

		clearGroupListBtn.setOnClickListener(new OnClickListener() {
			String CUSTOM_INTENT = "com.brighthalo.intent.action.TEST";
			@Override
			public void onClick(View v) {	
				listAngels.clear();
				Angel1.setText("");
			}
		  });
	}
	public void setGlobalBtnControls(){
		  Button skipBtn, doneBtn;
		  TextView screenTitle;
		  skipBtn     = (Button)   findViewById(R.id.button_skip);
		  skipBtn.setVisibility(View.GONE);
		  doneBtn     = (Button)   findViewById(R.id.button_done);	
		  doneBtn.setVisibility(View.GONE);
		  
		  screenTitle = (TextView) findViewById(R.id.activity_title);
		  screenTitle.setText("Select Your Angels");

	      skipBtn.setOnClickListener(new OnClickListener() {
	  		public void onClick(View v) {
	  			 Intent intent1 = new Intent(AngelGroupSetupActivity.this,MainDiscussionActivity.class);
	             startActivity(intent1);
	  		}
	  	  });
	      
	      doneBtn.setOnClickListener(new OnClickListener() {
	  		public void onClick(View v) {
	  			Intent intent1 = new Intent(AngelGroupSetupActivity.this,MainDiscussionActivity.class);
	  			intent1.putParcelableArrayListExtra("listOfAngels", listOfAngels);
	            startActivity(intent1);
	        }
	  	  });
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
