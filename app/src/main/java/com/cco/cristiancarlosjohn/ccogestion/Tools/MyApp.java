package com.cco.cristiancarlosjohn.ccogestion.Tools;

import android.app.Application;
import android.os.SystemClock;

/**
 * Created by cristian.zapata on 24-08-2017.
 */

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Este es solo para poder ver el Splash Screen durante 3 segundos
        SystemClock.sleep(3000);
    }
}
