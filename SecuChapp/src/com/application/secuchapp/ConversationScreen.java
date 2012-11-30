package com.application.secuchapp;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;


public class ConversationScreen extends Activity {
	
	 /**
	  * Create an instance of this activity using the 
	  * activity_main_Screen xml and set it as the 
	  * active screen
	  */
	 private ListView mList;
	 private ArrayList<String> arrayList;
	 private MyCustomAdapter mAdapter;
	 private TCPClient mTcpClient;
	    
	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 super.setTitle("Secure Chat");
		 setContentView(R.layout.activity_chat_screen);
		 /*
		  * Custom action bar that has no app icon
		  */
		 final ActionBar actionBar = getActionBar();
	     actionBar.setDisplayShowTitleEnabled(true);
	     actionBar.setDisplayShowHomeEnabled(false); 
	     
	     arrayList = new ArrayList<String>();
	     
	        final EditText editText = (EditText) findViewById(R.id.editText);
	        Button send = (Button)findViewById(R.id.send_button);
	 
	        //relate the listView from java to the one created in xml
	        mList = (ListView)findViewById(R.id.list);
	        mAdapter = new MyCustomAdapter(this, arrayList);
	        mList.setAdapter(mAdapter);
	 
	        // connect to the server
	        new connectTask().execute("");
	 
	        send.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View view) {
	 
	                String message = editText.getText().toString();
	 
	                //add the text in the arrayList
	                arrayList.add("Me: " + message);
	 
	                //sends the message to the server
	                if (mTcpClient != null) {
	                    mTcpClient.sendMessage(message);
	                }
	 
	                //refresh the list
	                mAdapter.notifyDataSetChanged();
	                editText.setText("");
	            }
	        });
	 
	    }
	 
	 public class connectTask extends AsyncTask<String,String,TCPClient> {
	 
	        protected TCPClient doInBackground(String... message) {
	 
	            //we create a TCPClient object and
	            mTcpClient = new TCPClient(new TCPClient.OnMessageReceived() {
	                public void messageReceived(String message) {
	                    //this method calls the onProgressUpdate
	                    publishProgress(message);
	                }
	            });
	            mTcpClient.run();
	 
	            return null;
	        }
	 
	        protected void onProgressUpdate(String... values) {
	            super.onProgressUpdate(values);
	 
	            //in the arrayList we add the messaged received from server
	            arrayList.add(values[0]);
	            // notify the adapter that the data set has changed. This means that new message received
	            // from server was added to the list
	            mAdapter.notifyDataSetChanged();
	        }
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
}
