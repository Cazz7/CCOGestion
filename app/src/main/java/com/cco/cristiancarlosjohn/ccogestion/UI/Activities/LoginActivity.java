package com.cco.cristiancarlosjohn.ccogestion.UI.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cco.cristiancarlosjohn.ccogestion.Model.DatosUsuario;
import com.cco.cristiancarlosjohn.ccogestion.R;
import com.cco.cristiancarlosjohn.ccogestion.Tools.Constantes;
import com.cco.cristiancarlosjohn.ccogestion.Tools.MyApp;
import com.cco.cristiancarlosjohn.ccogestion.Tools.Preferences;
import com.cco.cristiancarlosjohn.ccogestion.WEB.FirebaseInstanceIDService;
import com.cco.cristiancarlosjohn.ccogestion.WEB.VolleySingleton;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    private static final String TAG = LoginActivity.class.getSimpleName();

    private EditText editTextUsuario;
    private EditText editTextPassword;
    private Button btnLogin;
    private ProgressBar pgbLogin;

    private String usuarioOK;
    private String passwordOK;
    private Switch switchRemember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bindUI();// Declaración de elementos del layout
        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        setCredentialsIfExist(); //Ingresa los datos del login

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuario = editTextUsuario.getText().toString();
                String password = editTextPassword.getText().toString();
                login(usuario, password); // Confirma el usuario y registra su token
            }
        });
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
            //TODO: Crear un método para obtener el perfil
            editor.putString("usuario", usuario);
            editor.putString("pass", password);
            editor.putString("perfil", perfil);
            editor.apply();
        }
    }

    private void goToMain(String perfil) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //Toast toast = Toast.makeText(this, perfil , Toast.LENGTH_SHORT);
        //toast.show();
        intent.putExtra(Constantes.PERFILES, perfil );
        startActivity(intent);
    }

    private void setCredentialsIfExist() {
        String usuario = Preferences.getUserPrefs(prefs);
        String password = Preferences.getUserPassPrefs(prefs);
        if (!TextUtils.isEmpty(usuario) && !TextUtils.isEmpty(password)) {
            editTextUsuario.setText(usuario);
            editTextPassword.setText(password);
            switchRemember.setChecked(true);
        }
    }

    private void login(String usuario, final String password) {

        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        //Elementos a enviar a la request php
        map.put("usuario", usuario);
        map.put("password", password);
        map.put("token",FirebaseInstanceId.getInstance().getToken());

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);

        // Depurando objeto Json...
        Log.d(TAG, jobject.toString());

        if(checkFields(usuario, password)){
            usuarioOK = usuario;
            passwordOK = password;
            enableProgressBar();
            VolleySingleton.getInstance(this).addToRequestQueue(
                    new JsonObjectRequest(
                            Request.Method.POST,
                            Constantes.REGISTER_TOKEN,
                            jobject,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    // Procesar la respuesta del servidor
                                    hideProgressBar();
                                    procesarRespuesta(response);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    hideProgressBar();
                                    Log.d(TAG, "Error Volley: " + error.getMessage());
                                }
                            }

                    )
                    {
                        @Override
                        public Map<String, String> getHeaders() {
                            Map<String, String> headers = new HashMap<String, String>();
                            headers.put("Content-Type", "application/json; charset=utf-8");
                            headers.put("Accept", "application/json");
                            return headers;
                        }

                        @Override
                        public String getBodyContentType() {
                            return "application/json; charset=utf-8" + getParamsEncoding();
                        }
                    }
            );
        }
    }

    private void hideProgressBar() {
        pgbLogin.setVisibility(View.GONE);
        btnLogin.setEnabled(true);
        switchRemember.setEnabled(true);
    }

    private void enableProgressBar() {
        pgbLogin = (ProgressBar) findViewById(R.id.progressBarLogin);
        btnLogin.setEnabled(false); // Se desactiva mientras se espera la respuesta
        switchRemember.setEnabled(false);
        pgbLogin.setVisibility(View.VISIBLE);
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
                String perfil = response.getString("perfil");
                saveUserData(usuarioOK, passwordOK, perfil);
                saveOnPreferences(usuarioOK, passwordOK, perfil);
                goToMain(perfil);
            } else {
                Toast toast = Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT);
                toast.show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void saveUserData(String usuarioOK, String passwordOK, String perfil) {
        DatosUsuario.setUsuario(usuarioOK);
        DatosUsuario.setPassword(passwordOK);
        DatosUsuario.setPerfil(perfil);
        MyApp.setPerfil(perfil);
    }
}
