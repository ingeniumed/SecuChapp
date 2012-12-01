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

public class SettingScreen extends Activity {
	
	 @Override
	 /**
	  * Create an instance of this activity using the 
	  * activity_main_Screen xml and set it as the 
	  * active screen
	  */
	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 super.setTitle("Secure Chat");
		 setContentView(R.layout.activity_settings_screen);
		 
		 /*
		  * Custom action bar that has no app icon
		  */
		 final ActionBar actionBar = getActionBar();
	     actionBar.setDisplayShowTitleEnabled(true);
	     actionBar.setDisplayShowHomeEnabled(false); 
	     
	     Button cancel = (Button) findViewById (R.id.cancel);
	     cancel.setOnClickListener(new View.OnClickListener(){
	     	public void onClick(View view) {
	     		finish();
	     	}
	     });
	     
	     Button save = (Button) findViewById (R.id.save);
	     save.setOnClickListener(new View.OnClickListener(){
	     	public void onClick(View view) {
	     		finish();
	     	}
	     });
	     
	 }
	 
	 public boolean onCreateOptionsMenu(Menu menu) {
		 getMenuInflater().inflate(R.menu.activity_settings_menu, menu);
		 return true;
	 }
	 
	 public boolean onOptionsItemSelected(MenuItem item) {
		 // Handle item selection
		 switch (item.getItemId()) {
		 case R.id.menu_home:
			 finish();
			 return true;
		 default:
			 return super.onOptionsItemSelected(item);
		 }
	 }
}
