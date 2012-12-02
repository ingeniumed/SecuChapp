package com.application.secuchapp;

import java.util.ArrayList;
import com.application.secuchapp.TCPClientService.LocalBinder;
import android.app.ActionBar;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


/***************************************************************************************************************
 * 												CONVERSATION SCREEN
 *
 **************************************************************************************************************/

public class ConversationScreen extends Activity {
	
	 
	 private ListView mList;
	 private MyCustomAdapter mAdapter;
	 private TCPClientService mService;
	 private Listener listener; 
	 @SuppressWarnings("unused")
	private boolean mBound;
	 
	 private ArrayList<String> messages = new ArrayList<String>();
	 
	 /**
	  * ONCREATE
	  */
	 public void onCreate(Bundle savedInstanceState) {
		 
		 // Create an instance of the activity 
		 super.onCreate(savedInstanceState);
		 super.setTitle("Secure Chat");
		 setContentView(R.layout.activity_chat_screen);
		 
		 // Custom action bar that has no app icon
		 final ActionBar actionBar = getActionBar();
	     actionBar.setDisplayShowTitleEnabled(true);
	     actionBar.setDisplayShowHomeEnabled(false); 
	     
	     // Setup edit text field
	     final EditText editText = (EditText) findViewById(R.id.editText);
	     Button send = (Button)findViewById(R.id.send_button);
 
	     //Setup the ListVeiw
	     mList = (ListView)findViewById(R.id.list);
	     mAdapter = new MyCustomAdapter(this, messages);
	     mList.setAdapter(mAdapter);
        
        
	     //Start the TCP Client 
	     Intent intent = new Intent(this, TCPClientService.class);
	     startService(intent);
	     bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        
	     //Start the message Listener
	     listener = new Listener();
	     listener.start();
	     
	     //Setup the send button onClick Listener
	     send.setOnClickListener(new View.OnClickListener() {
	    	 public void onClick(View view) {
	    		 
	    		 String message = editText.getText().toString();
 
	    		 //Add the text in the arrayList
	    		 messages.add("Me: " + message);
 
	    		 //Sends the message to the server
	    		 if (mService != null) {
	    			 mService.sendMessage(message);
	    		 }
 
	    		 //Refresh the list
	    		 mAdapter.notifyDataSetChanged();
	    		 editText.setText("");
	    	 }
	    });
	 
	 }

	 
	 public boolean onCreateOptionsMenu(Menu menu) {
		 getMenuInflater().inflate(R.menu.activity_conversations_screen, menu);
		 return true;
	 }
	 
	 public boolean onOptionsItemSelected(MenuItem item) {
		 // Handle item selection
		 switch (item.getItemId()) {
		 case R.id.menu_home:
			 finish();
			 return true;
		 case R.id.menu_settings:
			 startActivity(new Intent(this, SettingScreen.class));
			 return true;
		 default:
			 return super.onOptionsItemSelected(item);
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
	    		while(true){
						if (mService.numNewMessages != 0) {
							//Grab latest message
							 messages.add(mService.getLatestMessage());
							 //Tell the adapter that the data set has changed
							 Log.e("ConversationScreen", "Message delivered to adapter");
							 mAdapter.notifyDataSetChanged();
							 //Just for shits
							 mAdapter.notifyDataSetChanged();

						}
	    		}
	    	}
	    }
}
