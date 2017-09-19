package com.cco.cristiancarlosjohn.ccogestion.UI.Fragments;

import android.content.Context;
import android.content.Intent;
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
import com.cco.cristiancarlosjohn.ccogestion.Model.DesaEventos;
import com.cco.cristiancarlosjohn.ccogestion.R;
import com.cco.cristiancarlosjohn.ccogestion.Tools.Constantes;
import com.cco.cristiancarlosjohn.ccogestion.UI.Activities.InsertDesaActivity;
import com.cco.cristiancarlosjohn.ccogestion.UI.Adapters.DesaEventosAdapter;
import com.cco.cristiancarlosjohn.ccogestion.WEB.VolleySingleton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


public class DesaeventosFragment extends Fragment {

    private static final String TAG = DesaeventosFragment.class.getSimpleName();
    private DesaEventosAdapter adapter;
    private RecyclerView lista;
    private RecyclerView.LayoutManager lManager;

    FloatingActionButton fab;
    private Gson gson = new Gson();

    public DesaeventosFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_desaeventos, container, false);

        lista = (RecyclerView) v.findViewById(R.id.recicladorDesaeventos);
        lista.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(getActivity());
        lista.setLayoutManager(lManager);

        // Cargar datos en el adaptador
        cargarAdaptador();

        // Obtener instancia del FAB
        fab = (FloatingActionButton) v.findViewById(R.id.fabDesaeventos);

        // Asignar escucha al FAB
        fab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Iniciar actividad de inserción
                        getActivity().startActivityForResult(
                                new Intent(getActivity(), InsertDesaActivity.class), 3);
                    }
                }
        );

        return v;
    }

    /**
     * Carga el adaptador con los DesaEventos obtenidos
     * en la respuesta
     */
    public void cargarAdaptador() {
        // Petición GET
        VolleySingleton.
                getInstance(getActivity()).
                addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.GET,
                                Constantes.GET,
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
                    // Obtener array "desaeventos" Json
                    JSONArray mensaje = response.getJSONArray("desaeventos");
                    // Parsear con Gson
                    DesaEventos[] desaeventos = gson.fromJson(mensaje.toString(), DesaEventos[].class);
                    // Inicializar adaptador
                    adapter = new DesaEventosAdapter(Arrays.asList(desaeventos), getActivity());
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
