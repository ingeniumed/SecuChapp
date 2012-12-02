package com.application.secuchapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
		 
		 TextView name = (TextView) findViewById (R.id.name);
		 TextView extension = (TextView) findViewById (R.id.extension);
		 TextView department = (TextView) findViewById (R.id.department);
		 TextView position = (TextView) findViewById (R.id.position);
		 TextView email = (TextView) findViewById (R.id.email);
		 String name1 = getIntent().getStringExtra("name"); 
		 name.setText(name1);
		 
		 Button new_convo = (Button) findViewById (R.id.new_convo);
	     new_convo.setOnClickListener(new View.OnClickListener(){
	     	public void onClick(View view) {
	     		Intent intent_2 = new Intent(view.getContext(),ConversationScreen.class);
	        	startActivityForResult(intent_2,0);
	     	}
	     });
	 }
}