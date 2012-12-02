package com.application.secuchapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
		 
		 TextView name = (TextView) findViewById (R.id.contact_name);
		 TextView extension = (TextView) findViewById (R.id.extension);
		 TextView department = (TextView) findViewById (R.id.department);
		 TextView position = (TextView) findViewById (R.id.position);
		 TextView email = (TextView) findViewById (R.id.email);
		 
		 
		 
	 }
}