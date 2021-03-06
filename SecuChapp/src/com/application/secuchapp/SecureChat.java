package com.application.secuchapp;

import com.application.secuchapp.TCPClientService.LocalBinder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/***************************************************************************************************************
 * 												LOGIN SCREEN
 *
 **************************************************************************************************************/
public class SecureChat extends Activity {
	
	private TCPClientService mService;	//This is the TCPClient Service which 
	@SuppressWarnings("unused")
	private boolean mBound;				//is the service bound or not?
	private Listener listener;			//Listener thread which grabs new messages from the 
	@SuppressWarnings("unused")
	private String latestMessage = "";	//Latest message received from the server (updated by the listener thread)
	
	private boolean isOnline = false; 			//Check if the client is online
	private boolean gotResponse = false;
	private String name1 = "";

	
    @Override
    /**
     * Creates the instance of the login screen
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secure_chat);
        super.setTitle("Secure Chat");
        
        //Start the TCP Client 
        Intent intent = new Intent(this, TCPClientService.class);
		startService(intent);
		bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        
		//Start the message listener
		listener = new Listener();
		listener.start();
	
	//Setup the username and password fields
	final EditText usernameField = (EditText) findViewById(R.id.email_address);
	final EditText passwordField = (EditText) findViewById(R.id.password);
	
    //Setup the login button
    Button log_in = (Button) findViewById (R.id.log_in);
    log_in.setOnClickListener(new View.OnClickListener(){
    	
    	//onClick for login button
		public void onClick(View view) {
			
			//Send login info to server
			String username = usernameField.getText().toString();
			String password = passwordField.getText().toString();
			
			mService.sendMessage("L" + "|" + username + "|" + password); 
			
			//Wait for response form server
			checkOnlineTask task = new checkOnlineTask();
			task.execute();
			listener.interrupt();
	   		
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
						mService.numNewMessages = 0;
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
        		//Setup intent for switching activities
    			Intent intent_1 = new Intent(getApplicationContext(),MainScreen.class);
    	   		overridePendingTransition(R.anim.rotate_out,R.anim.rotate_in);
    	   		startActivityForResult(intent_1,0);
			}else{
				Toast.makeText(getApplicationContext(), "INCORRECT", Toast.LENGTH_SHORT).show();

			}
		this.cancel(true);
        }
    }

}
