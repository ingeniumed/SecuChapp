package com.application.secuchapp;

import java.util.ArrayList;
import com.application.secuchapp.TCPClientService.LocalBinder;
import android.app.ActionBar;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

public class ContactScreen extends FragmentActivity implements ActionBar.OnNavigationListener {
	
	private static Boolean state = true;
	private TCPClientService mService;	//This is the TCPClient Service which 
	private boolean mBound;				//is the service bound or not?
	private Listener listener;			//Listener thread which grabs new messages from the 
	private ArrayList<String> contacts = new ArrayList<String>(); //online contacts list
	private ListView mContacts;
	private MyCustomAdapter mAdapter;
	 
	 @SuppressWarnings("unchecked")
	@Override
	 /**
	  * Create an instance of this activity using the 
	  * activity_main_Screen xml and set it as the 
	  * active screen
	  */
	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 super.setTitle("Secure Chat");
		 setContentView(R.layout.activity_contact_screen);
		 
		 mContacts = (ListView)findViewById(R.id.contacts);
	     mAdapter = new MyCustomAdapter(this, contacts);
	     mContacts.setAdapter(mAdapter);
	     
	     
		//Start the TCP Client 
        Intent intent = new Intent(this, TCPClientService.class);
		startService(intent);
		bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
	        
		//Start the message listener
		listener = new Listener();
		listener.start();
		 
		 /*
		  * Custom action bar that has no app icon
		  */
		 final ActionBar actionBar = getActionBar();
	     actionBar.setDisplayShowTitleEnabled(true);
	     actionBar.setDisplayShowHomeEnabled(false); 
	     actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
	     
	     actionBar.setListNavigationCallbacks(
	    		 // Specify a SpinnerAdapter to populate the dropdown list.
	    		 new ArrayAdapter(
	    				 actionBar.getThemedContext(),
	    				 android.R.layout.simple_list_item_1,
	    				 android.R.id.text1,
	    				 new String[]{
	    					 getString(R.string.title_section_2),
	    					 getString(R.string.title_section_1),
	    				 }),
	    				 this);
	     
	     Button conversation = (Button) findViewById (R.id.conversation);
	     conversation.setOnClickListener(new View.OnClickListener(){
	     	public void onClick(View view) { 
				
				listener.interrupt();
	     		
	     		Intent intent_2 = new Intent(view.getContext(),ConversationScreen.class);
	        	startActivityForResult(intent_2,0);
	     	}
	     });
	 }
	 public boolean onCreateOptionsMenu(Menu menu) {
		 getMenuInflater().inflate(R.menu.activity_secure_chat, menu);
		 return true;
	 }
	 
	 public boolean onOptionsItemSelected(MenuItem item) {
		 // Handle item selection
		 switch (item.getItemId()) {
		 case R.id.menu_logout:
			 finish();
			 return true;
		 case R.id.menu_new_conversation:
			 mService.sendMessage("C|");
			 //startActivity(new Intent(this, ConversationScreen.class));
			 return true;
		 case R.id.menu_settings:
			 startActivity(new Intent(this, SettingScreen.class));
			 return true;
		 case R.id.menu_search:
			 return true;
		 default:
			 return super.onOptionsItemSelected(item);
		 }
	 }
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		if (itemPosition==0){
			if (state){
				//do nothing
			}else if (!state){
				state = true;
				startActivity(new Intent(this, ContactScreen.class));
			}
			return true;
		}else if (itemPosition==1){
			state = false;
			startActivity(new Intent(this, MainScreen.class));
			return true;
		}
		return false;
	}
	
	@Override
    /**
     * OnStop method for Login Screen
     */
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }
    
    /**
	  * The service connection is needed to setup the connection to the service
	  */
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            LocalBinder binder = (LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };
    
    /**
	  * This Thread listens for messages from the server and handles them 
	  */
    private class Listener extends Thread{
    	public void run(){
    		while(mService == null);
    		while(!this.isInterrupted()){
					if (mService.numNewMessages != 0) {
						//Grab latest message
						 contacts.add(mService.getLatestMessage());
						 //Tell the adapter that the data set has changed
						 Log.e("ContactScreen", "Contact delivered to adapter");
						 mAdapter.notifyDataSetChanged();
						//latestMessage = mService.getLatestMessage();
					}
    		}
    	}
    }

}
