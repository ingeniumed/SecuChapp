package com.application.secuchapp;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;

public class MainScreen extends FragmentActivity implements ActionBar.OnNavigationListener {
	
	private static Boolean state = true;
	
	 @SuppressWarnings("unchecked")
	@Override
	 /**
	  * Create an instance of this activity using the 
	  * activity_main_Screen xml and set it as the 
	  * active screen
	  */
	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 super.setTitle("");
		 setContentView(R.layout.activity_main_screen);
		 
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
	    					 getString(R.string.title_section_1),
	    					 getString(R.string.title_section_2),
	    				 }),
	    				 this);
	     
	     Button conversation = (Button) findViewById (R.id.conversation);
	     conversation.setOnClickListener(new View.OnClickListener(){
	     	public void onClick(View view) {
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
			 startActivity(new Intent(this, SecureChat.class));
			 return true;
		 case R.id.menu_new_conversation:
			 startActivity(new Intent(this, ConversationScreen.class));
			 return true;
		 case R.id.menu_settings:
			 startActivity(new Intent(this, SettingScreen.class));
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
				startActivity(new Intent(this, MainScreen.class));
			}
			return true;
		}else if (itemPosition==1){
			state = false;
			startActivity(new Intent(this, ContactScreen.class));
			return true;
		}
		return false;
	}
	
    @Override
    public void onBackPressed() {
    	// nothing
    }
}
