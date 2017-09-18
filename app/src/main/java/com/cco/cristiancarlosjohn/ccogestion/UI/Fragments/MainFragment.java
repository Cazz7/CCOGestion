package com.cco.cristiancarlosjohn.ccogestion.UI.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cco.cristiancarlosjohn.ccogestion.Model.Radicados;
import com.cco.cristiancarlosjohn.ccogestion.Model.UserDataBase;
import com.cco.cristiancarlosjohn.ccogestion.R;
import com.cco.cristiancarlosjohn.ccogestion.Tools.Constantes;
import com.cco.cristiancarlosjohn.ccogestion.Tools.DataBaseHelper.UserDBHelper;
import com.cco.cristiancarlosjohn.ccogestion.Tools.MyApp;
import com.cco.cristiancarlosjohn.ccogestion.Tools.Preferences;
import com.cco.cristiancarlosjohn.ccogestion.UI.Activities.InsertActivity;
import com.cco.cristiancarlosjohn.ccogestion.UI.Adapters.RadicadosAdapter;

import com.cco.cristiancarlosjohn.ccogestion.WEB.VolleySingleton;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class MainFragment extends Fragment {

    //Etiqueta de depuracion
    private static final String TAG = MainFragment.class.getSimpleName();

    //Adaptador del recycler view
    private RadicadosAdapter adapter;

    //Instancia global del recycler view
    private RecyclerView lista;

    //instancia global del administrador
    private RecyclerView.LayoutManager lManager;

    //Instancia global del FAB
    FloatingActionButton fabAdd;
    private Gson gson = new Gson();

    //Base de datos de usuarios
    UserDBHelper dbUsers;

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_main, container, false);

        lista = (RecyclerView) v.findViewById(R.id.recRadNotif);
        lista.setHasFixedSize(true);

        dbUsers = new UserDBHelper(getActivity());

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(getActivity());
        lista.setLayoutManager(lManager);

        // Cargar datos en el adaptador
        cargarAdaptador();

        //Se obtiene el perfil
        String perfil = dbUsers.getProfile();

        // Obtener instancia del FAB
        fabAdd = (FloatingActionButton) v.findViewById(R.id.fabAdd);

        // Asignar escucha al FAB
        fabAdd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Iniciar actividad de inserción
                        getActivity().startActivityForResult(
                                new Intent(getActivity(), InsertActivity.class), 3);
                    }
                }
        );


        return v;
    }

    /**
     * Carga el adaptador con las metas obtenidas
     * en la respuesta
     */
    public void cargarAdaptador() {
        // Petición GET
        VolleySingleton.
                getInstance(getActivity()).
                addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.GET,
                                Constantes.GET_EVENTOS_CURSO,
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
    }

    /**
     * Interpreta los resultados de la respuesta y así
     * realizar las operaciones correspondientes
     *
     * @param response Objeto Json con la respuesta
     */
    private void procesarRespuesta(JSONObject response) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString("estado");

            switch (estado) {
                case "1": // EXITO
                    // Obtener array "metas" Json
                    JSONArray mensaje = response.getJSONArray("radicados");
                    // Parsear con Gson
                    Radicados[] radicados = gson.fromJson(mensaje.toString(), Radicados[].class);
                    // Inicializar adaptador
                    adapter = new RadicadosAdapter(Arrays.asList(radicados), getActivity());
                    // Setear adaptador a la lista
                    lista.setAdapter(adapter);
                    break;
                case "2": // FALLIDO
                    String mensaje2 = response.getString("mensaje");
                    Toast.makeText(
                            getActivity(),
                            mensaje2,
                            Toast.LENGTH_LONG).show();
                    break;
            }

        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
        }

    }


}