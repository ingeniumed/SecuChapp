package com.application.secuchapp;

import java.util.ArrayList;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/***************************************************************************************************************
 * This is the Service which handles the TCPClient 
 * 
 * This service runs in the background of the application no matter what activity we are in
 * It is a wrapper for TCPClient - it is how the Activities can communicate with the TCPClient
 *
 **************************************************************************************************************/
public class TCPClientService extends Service{
	
	private static final String TAG = "TCPClientService";
	private TCPClient mTcpClient;
	private Updater updater;
	private final IBinder mBinder = new LocalBinder();
	
	//Incoming messages from the server get added to the messages ArrayList
	private ArrayList<String> messages = new ArrayList<String>();
	public int numNewMessages = 0; //number of messages since the last getNewMessages()

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG,"onCreate");
		
		updater = new Updater();
		
		
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Log.d(TAG,"onStart");
		
		if(!updater.isRunning){
			updater.start();
			updater.isRunning = true;
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG,"onDestroy");
		
		if(updater.isRunning){
			updater.interrupt();
		}
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		return mBinder;
	}
	
	public class LocalBinder extends Binder {
        TCPClientService getService() {
            // Return this instance of TCPClientService so clients can call public methods
            return TCPClientService.this;
        }
    }
	
	private class Updater extends Thread {
		public boolean isRunning = false;

		public void run(){
			//we create a TCPClient object and setups the OnMessageRecieved
	        mTcpClient = new TCPClient(new TCPClient.OnMessageReceived() {
	            public void messageReceived(String message) {
	                //Handle the message
	                handleMessage(message);
	            }
	        });
	        mTcpClient.run();
		}
		
		public void handleMessage(String message){
			numNewMessages ++;
			messages.add(message);
			Log.e("ClientService","Handled message:" + message);
		}
	}
	
	
	public void sendMessage(String message){
		if(mTcpClient != null){
			mTcpClient.sendMessage(message);
		}
	}
	
	public String getLatestMessage(){
		if(messages.size() > 0){
			numNewMessages = 0;
			String message = messages.get(messages.size()-1);
			Log.e("ClientService","delivering message:" + message);
			return message;
		}
		else{
			return null;
		}
			
	}

}
