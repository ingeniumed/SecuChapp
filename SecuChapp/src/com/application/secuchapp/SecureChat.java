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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secure_chat);

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
