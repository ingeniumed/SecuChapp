package com.application.secuchapp;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


/***************************************************************************************************************
 * 												CONVERSATION SCREEN
 *
 **************************************************************************************************************/

public class ConversationScreen extends Activity {
	
	public static final String key ="corleone";
	 private ListView mList;
	 private MyCustomAdapter mAdapter;
	 private TCPClientService mService;
	 private Listener listener; 
	 private String receiver; 				//name of person who will be receiving the messages
	 String encrypted;
	 
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
		 
		 final String receiver = getIntent().getStringExtra("receiver");
		 
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
	    		 
	    		 
	    		 try {
					encrypted= new String(Cryptography.encrypt(key.getBytes("UTF-8"), message.getBytes("UTF-8")));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
	    		 
	    		 
	    		 messages.add("Me: " + message);
 
	    		 //Sends the message to the server
	    		 if (mService != null) {
	    			 mService.sendMessage("M"+"|"+receiver+"|"+encrypted);
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
							deliverMessage d = new deliverMessage();
							d.execute();
							mService.numNewMessages = 0;
						}
	    		}
	    	}
	    }
	 
	 private class deliverMessage extends AsyncTask<String, String, Void> {			
			
	    	@Override
	    	protected Void doInBackground(String... name) {
	        		publishProgress(name);
	        		return null;
	        }
			
			@Override
	        protected void onProgressUpdate(String... name) {
	        	//Raise a toast when if user is offline, otherwise switch to conversation screen
				//Grab latest message
				 messages.add(mService.getLatestMessage());
				 //Tell the adapter that the data set has changed
				 Log.e("ConversationScreen", "Message delivered to adapter");
				 mAdapter.notifyDataSetChanged();
	        }
	    }

}
