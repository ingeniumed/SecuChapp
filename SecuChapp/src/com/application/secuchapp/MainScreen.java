package com.application.secuchapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainScreen extends Activity {
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main_screen);

	    Button log_in = (Button) findViewById (R.id.log_in);
	    log_in.setOnClickListener(new View.OnClickListener(){
	    	public void onClick(View view) {
	    		Intent intent_2 = new Intent();
	    		setResult(RESULT_OK,intent_2);
	    		finish();
	    	}
	    	
	    });
	    
	    }
}
