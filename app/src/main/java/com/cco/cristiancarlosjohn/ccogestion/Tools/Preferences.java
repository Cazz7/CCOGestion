package com.cco.cristiancarlosjohn.ccogestion.Tools;

import android.content.SharedPreferences;

/**
 * Created by cristian.zapata on 24-08-2017.
 */

public class Preferences {
    public static String getUserPrefs(SharedPreferences preferences) {
        return preferences.getString("usuario", "");
    }

    public static String getUserPassPrefs(SharedPreferences preferences) {
        return preferences.getString("pass", "");
    }

    public static String getUserProfilePrefs(SharedPreferences preferences) {
        return preferences.getString("perfil", "");
    }

    public static void removeSharedPreferences(SharedPreferences preferences) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("usuario");
        editor.remove("pass");
        editor.remove("perfil");
        editor.apply();
    }
}
