package com.cco.cristiancarlosjohn.ccogestion.UI.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cco.cristiancarlosjohn.ccogestion.R;
import com.cco.cristiancarlosjohn.ccogestion.Tools.Constantes;
import com.cco.cristiancarlosjohn.ccogestion.UI.Fragments.MainFragment;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.mainContainer, new MainFragment(), "MainFragment")
                    .commit();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constantes.CODIGO_DETALLE || requestCode == 3) {
            if (resultCode == RESULT_OK || resultCode == 203) {
                MainFragment fragment = (MainFragment) getSupportFragmentManager().
                        findFragmentByTag("MainFragment");
                fragment.cargarAdaptador();
            }
        }
    }
}
