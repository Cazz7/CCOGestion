package com.cco.cristiancarlosjohn.ccogestion.WEB;

import android.app.Service;
import android.text.TextUtils;

import com.cco.cristiancarlosjohn.ccogestion.Tools.Constantes;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by cristian.zapata on 27/08/2017.
 */

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Override
    // Token received
    public void onTokenRefresh() {
        super.onTokenRefresh();

        FirebaseMessaging.getInstance().subscribeToTopic("test1");
        String token = FirebaseInstanceId.getInstance().getToken();
        //String token = "HOLAAMIGO2";
        //Send token into the DB
        if (!TextUtils.isEmpty(token) || (token != null) )
                registerToken(token);
    }

    private void registerToken(String token) {

        //Luego se hará sin volley
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("token",token)
                .build();

        Request request = new Request.Builder()
                .url(Constantes.REGISTER_TOKEN)
                .post(body)
                .build();

        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
