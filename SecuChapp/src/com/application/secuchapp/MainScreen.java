package com.application.secuchapp;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

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
	     
	  /**
	   * Declare an instance of the log out button from the
	   * xml file using its id. Then finish once its clicked.
	   */
		 Button log_out = (Button) findViewById (R.id.log_out);
		 log_out.setOnClickListener(new View.OnClickListener(){
			 public void onClick(View view) {
				 //Intent intent_2 = new Intent();
				 finish();
			 }

		 });

	 }

	 public boolean onCreateOptionsMenu(Menu menu) {
		 getMenuInflater().inflate(R.menu.activity_secure_chat, menu);
		 return true;
	 }
}
