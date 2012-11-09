package com.application.secuchapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
//IT WORKS
public class SecureChat extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secure_chat);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_secure_chat, menu);
        return true;
    }
}
