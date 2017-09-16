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
    public void onTokenRefresh() {
        super.onTokenRefresh();

        FirebaseMessaging.getInstance().subscribeToTopic("test1");
        FirebaseInstanceId.getInstance().getToken();
    }
}