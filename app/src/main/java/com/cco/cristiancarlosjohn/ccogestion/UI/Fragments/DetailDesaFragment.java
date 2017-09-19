package com.cco.cristiancarlosjohn.ccogestion.UI.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cco.cristiancarlosjohn.ccogestion.Model.DesaEventos;
import com.cco.cristiancarlosjohn.ccogestion.R;
import com.cco.cristiancarlosjohn.ccogestion.Tools.Constantes;
import com.cco.cristiancarlosjohn.ccogestion.UI.Activities.UpdateDesaActivity;
import com.cco.cristiancarlosjohn.ccogestion.WEB.VolleySingleton;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailDesaFragment extends Fragment {

    /**
     * Etiqueta de depuración
     */
    private static final String TAG = DetailDesaFragment.class.getSimpleName();

    /*
    Instancias de Views
     */
    private ImageView cabecera;
    //private TextView idRadicado;
    private TextView fecha;
    private TextView codEvento;
    private TextView subEvento;
    private TextView observaciones;
    private TextView usuario;
    private TextView estado;
    private TextView fechaIngSistema;
    private ImageButton editButton;
    private String extra;
    private Gson gson = new Gson();

    public DetailDesaFragment() {
    }

    public static DetailDesaFragment createInstance(String IdDesaeventos) {
        DetailDesaFragment detailFragment = new DetailDesaFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constantes.EXTRA_ID, IdDesaeventos);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail_desa, container, false);

        // Obtención de views
        cabecera = (ImageView) v.findViewById(R.id.cabecera);
        codEvento = (TextView) v.findViewById(R.id.evento);
        subEvento = (TextView)  v.findViewById(R.id.subEvento);
        observaciones = (TextView) v.findViewById(R.id.observacion);
        estado = (TextView) v.findViewById(R.id.estado);
        fecha = (TextView) v.findViewById(R.id.fecha);
        fechaIngSistema = (TextView) v.findViewById(R.id.fechaIng);
        usuario = (TextView) v.findViewById(R.id.usuario);
        editButton = (FloatingActionButton) v.findViewById(R.id.fabUpdate);

        // Obtener extra del intent de envío
        extra = getArguments().getString(Constantes.EXTRA_ID);

        // Setear escucha para el fab
        editButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Iniciar actividad de actualización
                        Intent i = new Intent(getActivity(), UpdateDesaActivity.class);
                        i.putExtra(Constantes.EXTRA_ID, extra);
                        getActivity().startActivityForResult(i, Constantes.CODIGO_ACTUALIZACION);
                    }
                }
        );

        // Cargar datos desde el web service
        cargarDatos();

        return v;
    }

    /**
     * Obtiene los datos desde el servidor
     */
    public void cargarDatos() {

        // Añadir parámetro a la URL del web service
        String newURL = Constantes.GET_BY_ID + "?IdDesaeventos=" + extra;

        // Realizar petición GET_BY_ID
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.GET,
                        newURL,
                        null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                // Procesar respuesta Json
                                procesarRespuesta(response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG, "Error Volley: " + error.getMessage());
                            }
                        }
                )
        );
    }

    /**
     * Procesa cada uno de los estados posibles de la
     * respuesta enviada desde el servidor
     *
     * @param response Objeto Json
     */


    private void procesarRespuesta(JSONObject response) {

        try {
            // Obtener atributo "mensaje"
            String mensaje = response.getString("estado");

            switch (mensaje) {
                case "1":
                    // Obtener objeto "desaeventos"
                    JSONObject object = response.getJSONObject("desaeventos");

                    //Parsear objeto
                    DesaEventos desaeventos = gson.fromJson(object.toString(), DesaEventos.class);

                    // Asignar color del fondo
                    switch (desaeventos.getCOD_EVENTO()) {
                        //TODO: Cambiar esto por lo de desaevento
                        case "Salud":
                            cabecera.setBackgroundColor(getResources().getColor(R.color.saludColor));
                            break;
                        case "Finanzas":
                            cabecera.setBackgroundColor(getResources().getColor(R.color.finanzasColor));
                            break;
                        case "Espiritual":
                            cabecera.setBackgroundColor(getResources().getColor(R.color.espiritualColor));
                            break;
                        case "Profesional":
                            cabecera.setBackgroundColor(getResources().getColor(R.color.profesionalColor));
                            break;
                        case "Material":
                            cabecera.setBackgroundColor(getResources().getColor(R.color.materialColor));
                            break;
                    }

                    // Seteando valores en los views
                    codEvento.setText(desaeventos.getCOD_EVENTO());
                    subEvento.setText(desaeventos.getSUB_EVENTO());
                    observaciones.setText(desaeventos.getOBSERVACIONES());
                    estado.setText(desaeventos.getEstado());
                    fecha.setText(desaeventos.getFECHA());
                    fechaIngSistema.setText(desaeventos.getFechaIngSistema());
                    usuario.setText(desaeventos.getUSUARIO());

                    break;

                case "2":
                    String mensaje2 = response.getString("mensaje");
                    Toast.makeText(
                            getActivity(),
                            mensaje2,
                            Toast.LENGTH_LONG).show();
                    break;

                case "3":
                    String mensaje3 = response.getString("mensaje");
                    Toast.makeText(
                            getActivity(),
                            mensaje3,
                            Toast.LENGTH_LONG).show();
                    break;
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
