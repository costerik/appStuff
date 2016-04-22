package com.example.ingerikahumada.stuff;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

//import com.firebase.client.Firebase;

public class MainActivity extends AppCompatActivity {

    //Firebase m_fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_main);

        Home home= new Home();

        //getFragmentManager().beginTransaction().add(R.id.fragment_container, home).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, home).commit();
    }
}