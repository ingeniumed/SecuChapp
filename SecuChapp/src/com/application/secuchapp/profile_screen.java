package com.application.secuchapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class profile_screen extends Activity {
	
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

	 }
}