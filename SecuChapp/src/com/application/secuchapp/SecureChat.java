package com.application.secuchapp;

import android.os.Bundle;
import android.content.Intent;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.animation.LayoutTransition;

public class SecureChat extends Activity {

    @Override
    /**
     * Creates the instance of the entire application
     * using the activity_secure_chat xml file that is 
     * the log in screen. 
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secure_chat);
        super.setTitle("Secure Chat");
        
    /**
     * Declare an instance of the log in button in the xml
     * file using its id set up in it. Then listen for its
     * click and push the main screen as an activity on top
     * to make it the current screen. While doing this do a 
     * neat animation in between to make it unbelievably epic.
     */
    Button log_in = (Button) findViewById (R.id.log_in);
    log_in.setOnClickListener(new View.OnClickListener(){
    	public void onClick(View view) {
    		Intent intent_1 = new Intent(view.getContext(),MainScreen.class);
       		overridePendingTransition(R.anim.rotate_out,R.anim.rotate_in);
       		startActivityForResult(intent_1,0);
    	}
    });
    
    }
}
