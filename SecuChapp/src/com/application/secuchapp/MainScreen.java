package com.application.secuchapp;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

public class MainScreen extends Activity {
	
	 @Override
	 /**
	  * Create an instance of this activity using the 
	  * activity_main_Screen xml and set it as the 
	  * active screen
	  */
	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 super.setTitle("Secure Chat");
		 setContentView(R.layout.activity_main_screen);
		 
		 /*
		  * Custom action bar that has no app icon
		  */
		 final ActionBar actionBar = getActionBar();
	     actionBar.setDisplayShowTitleEnabled(true);
	     actionBar.setDisplayShowHomeEnabled(false); 
	     
	     Button conversation = (Button) findViewById (R.id.conversation);
	     conversation.setOnClickListener(new View.OnClickListener(){
	     	public void onClick(View view) {
	     		Intent intent_2 = new Intent(view.getContext(),ConversationScreen.class);
	        		overridePendingTransition(R.anim.rotate_out,R.anim.rotate_in);
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
}
