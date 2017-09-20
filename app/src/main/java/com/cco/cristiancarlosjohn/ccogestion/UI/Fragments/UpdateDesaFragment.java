package com.cco.cristiancarlosjohn.ccogestion.UI.Fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cco.cristiancarlosjohn.ccogestion.Model.DesaEventos;
import com.cco.cristiancarlosjohn.ccogestion.R;
import com.cco.cristiancarlosjohn.ccogestion.Tools.Constantes;
import com.cco.cristiancarlosjohn.ccogestion.WEB.VolleySingleton;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateDesaFragment extends Fragment {

    /*
    Etiqueta de depuración
     */
    private static final String TAG = UpdateDesaFragment.class.getSimpleName();

    /*
    Controles
    */
    TextView idRadicadoInput;
    EditText eventoInput;
    EditText subEventoInput;
    EditText observacionesInput;
    TextView estadoInput;
    TextView fechaInput;
    TextView fechaIngInput;
    TextView usuarioInput;

    /*
    Valor del argumento extra
     */
    private String idDesaEventos;

    /**
     * Es la meta obtenida como respuesta de la petición HTTP
     */
    private DesaEventos desaEventoOriginal;

    /**
     * Instancia Gson para el parsing Json
     */
    private Gson gson = new Gson();


    public UpdateDesaFragment() {
    }

    /**
     * Crea un nuevo fragmento basado en un argumento
     *
     * @param extra Argumento de entrada
     * @return Nuevo fragmento
     */
    public static Fragment createInstance(String extra) {
        UpdateDesaFragment detailFragment = new UpdateDesaFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constantes.EXTRA_ID, extra);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflando layout del fragmento
        View v = inflater.inflate(R.layout.fragment_update_desa, container, false);

        // Obtención de instancias controles
        idRadicadoInput = (TextView) v.findViewById(R.id.idradicado_input_ff);
        eventoInput = (EditText) v.findViewById(R.id.evento_input_ff);
        subEventoInput = (EditText) v.findViewById(R.id.subEvento_input_ff);
        observacionesInput = (EditText) v.findViewById(R.id.observaciones_input_ff);
        estadoInput = (TextView) v.findViewById(R.id.estado_input_ff);
        fechaInput = (TextView) v.findViewById(R.id.fecha_input_ff);
        fechaIngInput = (TextView) v.findViewById(R.id.fechaIng_input_ff);
        usuarioInput = (TextView) v.findViewById(R.id.usuario_input_ff);

        fechaInput.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogFragment picker = new DatePickerFragment();
                        picker.show(getFragmentManager(), "datePicker");

                    }
                }
        );

        fechaIngInput.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogFragment picker = new DatePickerFragment();
                        picker.show(getFragmentManager(), "datePicker");

                    }
                }
        );

        // Obtener valor extra
        idDesaEventos = getArguments().getString(Constantes.EXTRA_ID);

        if (idDesaEventos != null) {
            cargarDatos();
        }

        return v;
    }

    /**
     * Obtiene los datos desde el servidor
     */
    private void cargarDatos() {
        // Añadiendo IdDesaeventos como parámetro a la URL
        String newURL = Constantes.GET_BY_ID + "?IdDesaeventos=" + idDesaEventos;

        // Consultar el detalle del DesaEvento
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.GET,
                        newURL,
                        null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                // Procesa la respuesta GET_BY_ID
                                procesarRespuestaGet(response);
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

    // todo: Revisar para recivir el parametro del activity Radicado
    /**
     * Procesa la respuesta de obtención obtenida desde el sevidor  *
     */
    private void procesarRespuestaGet(JSONObject response) {

        try {
            String estado = response.getString("estado");

            switch (estado) {
                case "1":
                    JSONObject meta = response.getJSONObject("desaeventos");
                    // Guardar instancia
                    desaEventoOriginal = gson.fromJson(meta.toString(), DesaEventos.class);
                    // Setear valores de la meta
                    cargarViews(desaEventoOriginal);
                    break;

                case "2":
                    String mensaje = response.getString("mensaje");
                    // Mostrar mensaje
                    Toast.makeText(
                            getActivity(),
                            mensaje,
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
     * Carga los datos iniciales del formulario con los
     * atributos de un objeto {@link DesaEventos}
     *
     * @param desaeventos Instancia
     */
    private void cargarViews(DesaEventos desaeventos) {
        // Seteando valores de la respuesta
        idRadicadoInput.setText(desaeventos.getIdRadicado());
        eventoInput.setText(desaeventos.getCOD_EVENTO());
        subEventoInput.setText(desaeventos.getSUB_EVENTO());
        observacionesInput.setText(desaeventos.getOBSERVACIONES());
        estadoInput.setText(desaeventos.getEstado());
        fechaInput.setText(desaeventos.getFECHA());
        fechaIngInput.setText(desaeventos.getFechaIngSistema());
        usuarioInput.setText(desaeventos.getUSUARIO());


        /*// Obteniendo acceso a los array de strings para categorias y prioridades
        String[] categorias = getResources().getStringArray(R.array.entradas_categoria);
        String[] prioridades = getResources().getStringArray(R.array.entradas_prioridad);*/

        /*// Obteniendo la posición del spinner categorias
        int posicion_categoria = 0;
        for (int i = 0; i < categorias.length; i++) {
            if (categorias[i].compareTo(meta.getCategoria()) == 0) {
                posicion_categoria = i;
                break;
            }
        }*/

        /*// Setear selección del Spinner de categorías
        categoria_spinner.setSelection(posicion_categoria);

        // Obteniendo la posición del spinner de prioridades
        int posicion_prioridad = 0;
        for (int i = 0; i < prioridades.length; i++) {
            Log.d(TAG, "posición:" + i);
            if (prioridades[i].compareTo(meta.getPrioridad()) == 0) {
                posicion_prioridad = i;

                break;
            }
        }

        // Setear selección del Spinner de prioridades
        prioridad_spinner.setSelection(posicion_prioridad);*/
    }

    /**
     * Compara los datos actuales con aquellos que se obtuvieron
     * por primera vez en la respuesta HTTP
     *
     * @return true si los datos no han cambiado, de lo contrario false
     */
    public boolean validarCambios() {
        return desaEventoOriginal.compararCon(obtenederDatos());
    }

    /**
     * Retorna en una nueva meta creada a partir
     * de los datos del formulario actual
     *
     * @return Instancia {@link DesaEventos}
     */
    private DesaEventos obtenederDatos() {

        String idRadicado = idRadicadoInput.getText().toString();
        String evento = eventoInput.getText().toString();
        String subEvento = subEventoInput.getText().toString();
        String observaciones = observacionesInput.getText().toString();
        String estado = estadoInput.getText().toString();
        String fecha = fechaInput.getText().toString();
        String fechaIng = fechaIngInput.getText().toString();
        String usuario = usuarioInput.getText().toString();
        //String prioridad = (String) prioridad_spinner.getSelectedItem();

        return new DesaEventos("0", idRadicado, evento, subEvento, observaciones, estado, fecha, fechaIng, usuario);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); // Contribución a la AB
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:// CONFIRMAR
                if (!validarCambios())
                    guardarDesaEventos();
                else
                    // Terminar actividad, ya que no hay cambios
                    getActivity().finish();
                return true;

            case R.id.action_delete:// ELIMINAR
                mostrarDialogo(R.string.dialog_delete_msg);
                break;

            case R.id.action_discard:// DESCARTAR
                if (!validarCambios()) {
                    mostrarDialogo(R.string.dialog_discard_msg);
                } else
                    // Terminar actividad, ya que no hay cambios
                    getActivity().finish();
                break;

        }
        ;

        return super.onOptionsItemSelected(item);
    }

    /**
     * Guarda los cambios de un desaEvento editado.
     * <p>
     * Si está en modo inserción, entonces crea un nuevo
     * DesaEvevento en la base de datos
     */
    private void guardarDesaEventos() {

        // Obtener valores actuales de los controles
        final String idRadicado = idRadicadoInput.getText().toString();
        final String evento = eventoInput.getText().toString();
        final String subEvento = subEventoInput.getText().toString();
        final String observacion = observacionesInput.getText().toString();
        final String estado = estadoInput.getText().toString();
        final String fecha = fechaInput.getText().toString();
        final String fechaIng = fechaIngInput.getText().toString();
        final String usuario = usuarioInput.getText().toString();
        // final String prioridad = prioridad_spinner.getSelectedItem().toString();

        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("IdDesaeventos", idDesaEventos);
        map.put("IdRadicado", idRadicado);
        map.put("FECHA", fecha);
        map.put("COD_EVENTO", evento);
        map.put("SUB_EVENTO", subEvento);
        map.put("OBSERVACIONES", observacion);
        map.put("USUARIO", usuario);
        map.put("Estado", estado);
        map.put("FechaIngSistema", fechaIng);

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);

        // Depurando objeto Json...
        Log.d(TAG, jobject.toString());

        // Actualizar datos en el servidor
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.POST,
                        Constantes.UPDATE,
                        jobject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                procesarRespuestaActualizar(response);
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

    /**
     * Procesa todos las tareas para eliminar
     * un DesaEvento en la aplicación. Este método solo se usa
     * en la edición
     */
    /*public void eliminarDesaEvento() {

        HashMap<String, String> map = new HashMap<>();// MAPEO

        map.put("IdDesaeventos", idDesaEventos);// Identificador

        JSONObject jobject = new JSONObject(map);// Objeto Json

        // Eliminar datos en el servidor
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.POST,
                        Constantes.DELETE,
                        jobject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // Procesar la respuesta
                                procesarRespuestaEliminar(response);

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

    /**
     * Procesa la respuesta de eliminación obtenida desde el sevidor
     */
    /*private void procesarRespuestaEliminar(JSONObject response) {

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
                            mensaje,
                            Toast.LENGTH_LONG).show();
                    // Enviar código de éxito
                    getActivity().setResult(203);
                    // Terminar actividad
                    getActivity().finish();
                    break;

                case "2":
                    // Mostrar mensaje
                    Toast.makeText(
                            getActivity(),
                            mensaje,
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

    }*/

    /**
     * Procesa la respuesta de actualización obtenida desde el sevidor
     */
    private void procesarRespuestaActualizar(JSONObject response) {

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
                            mensaje,
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
                            mensaje,
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
     * Actualiza la fechas de los campos {@link fechaInput} y {@link fechaIngInput}
     *
     * @param ano Año
     * @param mes Mes
     * @param dia Día
     */
    public void actualizarFecha(int ano, int mes, int dia) {
        // Setear en el textview la fecha
        fechaInput.setText(ano + "-" + (mes + 1) + "-" + dia);
        fechaIngInput.setText(ano + "-" + (mes + 1) + "-" + dia);
    }

    /**
     * Muestra un diálogo de confirmación, cuyo mensaje esta
     * basado en el parámetro identificador de Strings
     *
     * @param id Parámetro
     */
    private void mostrarDialogo(int id) {
        DialogFragment dialogo = ConfirmDialogFragment.
                createInstance(
                        getResources().
                                getString(id));
        dialogo.show(getFragmentManager(), "ConfirmDialog");
    }

}
