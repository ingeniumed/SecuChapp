package com.application.secuchapp;

import java.util.ArrayList;
import com.application.secuchapp.TCPClientService.LocalBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class profile_screen extends Activity {
	
	private TCPClientService mService;			//This is the TCPClient Service which 
	private boolean mBound;						//is the service bound or not?
	private Listener listener;					//Listener thread which grabs new messages from the 
	private boolean isOnline = false; 			//Check if the client is online
	private boolean gotResponse = false;
	private ListView mContacts;
	private MyCustomAdapter mAdapter;
	private String name1 = "";
	
	 @Override
	 /**
	  * Create an instance of this activity using the 
	  * profile_screen.xml and set it as the 
	  * active screen
	  */
	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.profile_screen);
		 super.setTitle("Secure Chat");
		 
		 TextView name = (TextView) findViewById (R.id.name);
		 TextView extension = (TextView) findViewById (R.id.extension);
		 TextView department = (TextView) findViewById (R.id.department);
		 TextView position = (TextView) findViewById (R.id.position);
		 TextView email = (TextView) findViewById (R.id.email);
		 name1 = getIntent().getStringExtra("name"); 
		 name.setText(name1);
		 
		//Start the TCP Client 
        Intent intent = new Intent(this, TCPClientService.class);
		startService(intent);
		bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
	        
		//Start the message listener
		listener = new Listener();
		listener.start();
		 
		 Button new_convo = (Button) findViewById (R.id.new_convo);
	     new_convo.setOnClickListener(new View.OnClickListener(){
	     	public void onClick(View view) {
	     		
	     		//Ask server if user is online
				mService.sendMessage("O" + "|" + name1); 
				
				//Wait for response form server
				checkOnlineTask task = new checkOnlineTask();
				task.execute();
	     	}
	     });
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
							String latestMessage = mService.getLatestMessage();
							gotResponse = true;
							if(latestMessage.charAt(0) == 'T'){
								Log.e("ProfileScreen",latestMessage);
								isOnline = true;
							}
													
						}
	    		} 
	    	}
	    }
	    @Override
	    public void onStart(){
	    	super.onStart();
	    	
	    	listener = new Listener();
			listener.start();
	    }
	    
	    private class checkOnlineTask extends AsyncTask<String, String, Void> {			
			
	    	@Override
	    	protected Void doInBackground(String... name) {
					Log.e("checkOnlineTalk",name1);
	        		while(!gotResponse); //Wait for response from server
	        		gotResponse = false;
	        		publishProgress(name);
	        		return null;
	        }
			
			@Override
	        protected void onProgressUpdate(String... name) {
	        	//Raise a toast when if user is offline, otherwise switch to conversation screen
	        	if(isOnline){
	        		listener.interrupt();
		     		Intent intent_2 = new Intent(getApplicationContext(),ConversationScreen.class);
		        	intent_2.putExtra("receiver", name1);
		     		startActivityForResult(intent_2,0);
				}else{
					Toast.makeText(getApplicationContext(), "OFFLINE", Toast.LENGTH_SHORT).show();

				}
	        }
	    }
}