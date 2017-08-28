package com.cco.cristiancarlosjohn.ccogestion.UI.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cco.cristiancarlosjohn.ccogestion.R;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseMessaging.getInstance().subscribeToTopic("test1");
        FirebaseInstanceId.getInstance().getToken();
    }
}
