package com.cco.cristiancarlosjohn.ccogestion.UI.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cco.cristiancarlosjohn.ccogestion.R;
import com.cco.cristiancarlosjohn.ccogestion.Tools.Constantes;
import com.cco.cristiancarlosjohn.ccogestion.Tools.Preferences;
import com.cco.cristiancarlosjohn.ccogestion.WEB.FirebaseInstanceIDService;
import com.cco.cristiancarlosjohn.ccogestion.WEB.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    private static final String TAG = LoginActivity.class.getSimpleName();
    private static String perfil;

    private EditText editTextUsuario;
    private EditText editTextPassword;
    private Switch switchRemember;
    private Button btnLogin;
    private boolean bLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bindUI();// Declaraión de elementos del layout

        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        setCredentialsIfExist(); //Ingresa los datos del login

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuario = editTextUsuario.getText().toString();
                String password = editTextPassword.getText().toString();
                login(usuario, password);
                if (bLogin == true) {
                    if (!perfil.isEmpty() && perfil != null) {
                        getTokenAndRegister(usuario, perfil);
                        goToMain();
                        saveOnPreferences(usuario, password, perfil);
                    }
                }
            }
        });
    }

    private void getTokenAndRegister(String usuario, String perfil) {
        FirebaseInstanceIDService firebaseService = new FirebaseInstanceIDService();
        firebaseService.onTokenRefresh(usuario, perfil);
    }

    private void bindUI() {
        editTextUsuario = (EditText) findViewById(R.id.editTextUser);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        switchRemember = (Switch) findViewById(R.id.switchRemember);
        btnLogin = (Button) findViewById(R.id.buttonLogin);
    }

    private void saveOnPreferences(String usuario, String password, String perfil) {
        if (switchRemember.isChecked()) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("usuario", usuario);
            editor.putString("pass", password);
            editor.putString("perfil", perfil);
            editor.apply();
        }
    }

    private void goToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void setCredentialsIfExist() {
        String email = Preferences.getUserPrefs(prefs);
        String password = Preferences.getUserPassPrefs(prefs);
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            editTextUsuario.setText(email);
            editTextPassword.setText(password);
            switchRemember.setChecked(true);
        }
    }

    private void login(String usuario, String password) {

        if(checkFields(usuario, password)){
            // Añadir parámetro a la URL del web service
            String newURL = Constantes.GET_USERS + "/?USUARIO=" + usuario + "&Password=" +  password;
            // Petición GET
            VolleySingleton.
                    getInstance(this).
                    addToRequestQueue(
                            new JsonObjectRequest(
                                    Request.Method.GET,
                                    newURL,
                                    null,
                                    new Response.Listener<JSONObject>() {

                                        @Override
                                        public void onResponse(JSONObject response) {
                                            // Procesar la respuesta Json
                                            procesarRespuesta(response);
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.d(TAG, "Error Volley: " + error.toString());
                                        }
                                    }

                            )
                    );
        }else{
            bLogin = false; //Login fallido
        }

    }

    private boolean checkFields(String usuario, String password) {
        if (usuario == null || usuario.isEmpty()) {
            editTextUsuario.setError(getResources().getString(R.string.enter_user));
            return false;
        } else if (password == null || password.isEmpty()) {
            editTextPassword.setError(getResources().getString(R.string.enter_pass));
            return false;
        } else {
            return true;
        }
    }

    /**
     * Interpreta los resultados de la respuesta y así
     * realizar las operaciones correspondientes
     *
     * @param response Objeto Json con la respuesta
     */
    private void procesarRespuesta(JSONObject response) {

        try {
            String estado = response.getString("estado");
            if (estado.equals("1")) {
                bLogin = true;
                perfil = response.getString("perfil");
            } else {
                Toast toast = Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT);
                toast.show();
                bLogin = false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
