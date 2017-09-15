package com.cco.cristiancarlosjohn.ccogestion.UI.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cco.cristiancarlosjohn.ccogestion.R;
import com.cco.cristiancarlosjohn.ccogestion.Tools.Constantes;
import com.cco.cristiancarlosjohn.ccogestion.WEB.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InsertFragment extends Fragment {
    /**
     * Etiqueta para depuración
     */
    private static final String TAG = InsertFragment.class.getSimpleName();
    public String notificacion_unidades;
    Constantes contantes=new Constantes();
    /*
    Controles
    */
    EditText kiloSector_input, Nombre_Reporta_input, Contacto_input, Observaciones_input;
    Spinner Cod_Evento_spinner, Via_spinner;
    TextView fecha_text, Notificaciones_text;




    public InsertFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Habilitar al fragmento para contribuir en la action bar
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflando layout del fragmento
        View v = inflater.inflate(R.layout.fragment_insert, container, false);

        // Obtención de instancias controles
        kiloSector_input = (EditText) v.findViewById(R.id.kiloSector_input);
        Nombre_Reporta_input = (EditText) v.findViewById(R.id.Nombre_Reporta_input);
        Contacto_input = (EditText) v.findViewById(R.id.Contacto_input);
        fecha_text = (TextView) v.findViewById(R.id.fecha_ejemplo_text);
        Cod_Evento_spinner = (Spinner) v.findViewById(R.id.Cod_Evento_spinner);
        Via_spinner = (Spinner) v.findViewById(R.id.Via_spinner);
        Observaciones_input = (EditText) v.findViewById(R.id.Observaciones_input);
        Notificaciones_text = (TextView) v.findViewById(R.id.Notificaciones_texto);

        //Obtener fecha y hora actual
        java.util.Date fechaActual=new java.util.Date();
        DateFormat formatofechas = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        fecha_text.setText(formatofechas.format(fechaActual));

        Notificaciones_text.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // DialogFragment picker = new DatePickerFragment();
                        // picker.show(getFragmentManager(), "datePicker");
                        validarnotificaciones();
                    }

                    private void validarnotificaciones() {
                        // where we will store or remove selected items
                        final ArrayList<Integer> mSelectedItems = new ArrayList<Integer>();
                        //Obtengo String del array unidades_operacion
                        final String[] unidades_operacion_String = getResources().getStringArray(R.array.unidades_operacion);

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                        // set the dialog title
                        builder.setTitle("Seleccione unidades a notificar")
                                // specify the list array, the items to be selected by default (null for none),
                                // and the listener through which to receive call backs when items are selected
                                // R.array.choices were set in the resources res/values/strings.xml


                                .setMultiChoiceItems(R.array.unidades_operacion, null, new DialogInterface.OnMultiChoiceClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                                        if (isChecked) {
                                            // if the user checked the item, add it to the selected items
                                            mSelectedItems.add(which);
                                        }

                                        else if (mSelectedItems.contains(which)) {
                                            // else if the item is already in the array, remove it
                                            mSelectedItems.remove(Integer.valueOf(which));
                                        }
                                    }

                                })

                                // Set the action buttons
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int id) {

                                        // user clicked OK, so save the mSelectedItems results somewhere
                                        // here we are trying to retrieve the selected items indices
                                        notificacion_unidades = "GESTION_VIAL";
                                        for(Integer i : mSelectedItems){
                                            notificacion_unidades +=","+unidades_operacion_String[i];
                                        }
                                        Toast.makeText(
                                                getActivity(),
                                                "NOTIFICACION: " + notificacion_unidades,
                                                Toast.LENGTH_LONG).show();


                                    }
                                })

                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int id) {
                                        // removes the AlertDialog in the screen
                                    }
                                })

                                .show();
                    }
                }

        );

        //llamada DatePickerFragment

       /* fecha_text.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogFragment picker = new DatePickerFragment();
                        picker.show(getFragmentManager(), "datePicker");

                    }
                }
        );*/

        return v;
    }

    private void showDialogNotificaciones(){

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        // Remover el action button de borrar
        menu.removeItem(R.id.action_delete);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:// CONFIRMAR
                if (!camposVacios()) {
                    guardarNuevoEvento();
                }
                else
                    Toast.makeText(
                            getActivity(),
                            "Completa los campos",
                            Toast.LENGTH_LONG).show();
                return true;

            case R.id.action_discard:// DESCARTAR
                if (!camposVacios())
                    mostrarDialogo();
                else
                    getActivity().finish();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    private void notificar() {
        final String Cod_Evento = Cod_Evento_spinner.getSelectedItem().toString();
        final String Via = Via_spinner.getSelectedItem().toString();
        final String kiloSector = kiloSector_input.getText().toString();
        final String Fecha = fecha_text.getText().toString();

        String Radicado = contantes.getRadicado();

        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put(Constantes.PERFILES, notificacion_unidades);
        map.put(Constantes.RADICADO, Radicado);
        map.put(Constantes.COD_EVENTO, Cod_Evento);
        map.put(Constantes.VIA, Via);
        map.put(Constantes.SECTOR, kiloSector);


        JSONObject jobject = new JSONObject(map);

        // Depurando objeto Json...
        Log.d(TAG, jobject.toString());

        // Actualizar datos en el servidor
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.POST,
                        Constantes.GET_NOTIFICACIONES,
                        jobject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // Procesar la respuesta del servidor
                                procesarRespuestaNotificación(response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG, "Error Volley: " + error.getMessage());
                            }
                        }

                ) {
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




    //INSERTAR NUEVO EVENTO
    public void guardarNuevoEvento() {
        //Obtener fecha y hora actual
        java.util.Date fechaActual=new java.util.Date();
        DateFormat formatofechas = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String FechaIngSistema= (formatofechas.format(fechaActual));

        // Obtener valores actuales de los controles
        final String Nombre_Reporta = Nombre_Reporta_input.getText().toString();
        final String Contacto = Contacto_input.getText().toString();
        final String kiloSector = kiloSector_input.getText().toString();
        final String Fecha = fecha_text.getText().toString();
        final String Cod_Evento = Cod_Evento_spinner.getSelectedItem().toString();
        final String Via = Via_spinner.getSelectedItem().toString();
        final String Observaciones = Observaciones_input.getText().toString();

        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("Cod_Evento", Cod_Evento);
        map.put("Fecha_Creacion", Fecha);
        map.put("Via", Via);
        map.put("kiloSector", kiloSector);
        map.put("kilometro", "");
        map.put("Estado", "ABIERTO");
        map.put("Usuario", "CCORTINEZ");
        map.put("Nombre", Nombre_Reporta);
        map.put("Contacto", Contacto);
        map.put("FechaCierreEvento", Fecha);
        map.put("OBSERVACIONES" ,Observaciones);
        map.put("SUB_EVENTO", "");
        map.put("FechaIngSistema", FechaIngSistema);

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);

        // Depurando objeto Json...
        Log.d(TAG, jobject.toString());

        // Actualizar datos en el servidor
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.POST,
                        Constantes.INSERT_NUEVO_EVENTO,
                        jobject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // Procesar la respuesta del servidor
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
/*VolleySingleton.getInstance(getActivity()).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.POST,
                        Constantes.INSERT_NUEVO_EVENTO,
                        jobject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // Procesar la respuesta del servidor
                                procesarRespuesta(response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG, "Error Volley: " + error.getMessage());
                            }
                        }

                ) {
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

    }*/

    private void procesarRespuestaNotificación(JSONObject response) {
        try {
            // Obtener estado
            String estado = response.getString("estado");
            // Obtener mensaje
            String mensaje = response.getString("mensaje");

            switch (estado) {
                case "1":
                    // Mostrar mensaje
                    Toast.makeText(
                            getActivity(),
                            "Notificacion exitosa",
                            Toast.LENGTH_LONG).show();
                    // Enviar código de éxito
                    getActivity().setResult(Activity.RESULT_OK);
                    // Terminar actividad
                    getActivity().finish();
                    break;

                case "2":
                    // Mostrar mensaje
                    Toast.makeText(
                            getActivity(),
                            "Error en notificación",
                            Toast.LENGTH_LONG).show();
                    // Enviar código de falla
                    getActivity().setResult(Activity.RESULT_CANCELED);
                    // Terminar actividad
                    getActivity().finish();
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    /**
     * Procesa la respuesta obtenida desde el sevidor
     *
     * @param response Objeto Json
     */
    private void procesarRespuesta(JSONObject response) {

        try {
            // Obtener estado
            String estado = response.getString("estado");
            // Obtener mensaje
            contantes.setRadicado(response.getString("mensaje"));

            switch (estado) {
                case "1":
                    // Mostrar mensaje
                    notificar();
                    Toast.makeText(
                            getActivity(),
                            "Su Radicado es: "+contantes.getRadicado(),
                            Toast.LENGTH_LONG).show();

                    // Enviar código de éxito
                    getActivity().setResult(Activity.RESULT_OK);
                    // Terminar actividad
                    getActivity().finish();
                    break;

                case "2":
                    // Mostrar mensaje
                    Toast.makeText(
                            getActivity(),
                            contantes.getRadicado(),
                            Toast.LENGTH_LONG).show();
                    // Enviar código de falla
                    getActivity().setResult(Activity.RESULT_CANCELED);
                    // Terminar actividad
                    getActivity().finish();
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * Valida si los campos {@lin titulo_input} y {@lin descripcion_input}
     * se han rellenado
     *
     * @return true si alguno o dos de los campos están vacios, false si ambos
     * están completos
     */
    public boolean camposVacios() {
        // String titulo = titulo_input.getText().toString();
        //String descripcion = descripcion_input.getText().toString();

        //return (titulo.isEmpty() || descripcion.isEmpty());
        return false;
    }

    /**
     * Actualiza la fecha del campo {@lin fecha_text}
     *
     * @param ano Año
     * @param mes Mes
     * @param dia Día
     */
    public void actualizarFecha(int ano, int mes, int dia) {
        // Setear en el textview la fecha
        fecha_text.setText(ano + "-" + (mes + 1) + "-" + dia);
    }

    /**
     * Muestra un diálogo de confirmación
     */
    public void mostrarDialogo() {
        DialogFragment dialogo = ConfirmDialogFragment.
                createInstance(
                        getResources().
                                getString(R.string.dialog_discard_msg));
        dialogo.show(getFragmentManager(), "ConfirmDialog");
    }

}
