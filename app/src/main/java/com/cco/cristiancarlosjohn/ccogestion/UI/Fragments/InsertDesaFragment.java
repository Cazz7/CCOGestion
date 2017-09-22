package com.cco.cristiancarlosjohn.ccogestion.UI.Fragments;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
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
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cco.cristiancarlosjohn.ccogestion.R;
import com.cco.cristiancarlosjohn.ccogestion.Tools.Constantes;
import com.cco.cristiancarlosjohn.ccogestion.Tools.DataBaseHelper.UserDBHelper;
import com.cco.cristiancarlosjohn.ccogestion.UI.Activities.InsertDesaActivity;
import com.cco.cristiancarlosjohn.ccogestion.WEB.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InsertDesaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class InsertDesaFragment extends Fragment {

    //Etiqueta para depuración
    private static final String TAG = InsertDesaFragment.class.getSimpleName();

    //Elementos
    TextView idRadicadoInput;
    EditText eventoInput;
    Spinner subEventoInput;
    EditText observacionesInput;
    EditText estadoInput;
    EditText fechaInput;
    //TextView fechaIngInput;
    EditText hora_editext;

    //DbHelper
    UserDBHelper userDB = new UserDBHelper(getActivity());

    public InsertDesaFragment() {
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
        View v = inflater.inflate(R.layout.fragment_insert_desa, container, false);

        // Obtención de instancias controles
        idRadicadoInput = (TextView) v.findViewById(R.id.idradicado_input_fi);
        subEventoInput = (Spinner) v.findViewById(R.id.subEvento_input_fi);
        observacionesInput = (EditText) v.findViewById(R.id.observaciones_input_fi);
        fechaInput = (EditText) v.findViewById(R.id.fecha_input_fi);
        hora_editext = (EditText) v.findViewById(R.id.hora_editText);


        //Obtener fecha y hora actual
        java.util.Date fechaActual = new java.util.Date();
        DateFormat formatofechas = new SimpleDateFormat("yyyy/MM/dd");
        fechaInput.setText(formatofechas.format(fechaActual));
        java.util.Date horaActual = new java.util.Date();
        DateFormat formatohoras = new SimpleDateFormat("HH:mm");
        hora_editext.setText(formatohoras.format(horaActual));

        setDefaultFields();

        // Todo: Revisar Error faltante
        fechaInput.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogFragment picker = new DatePickerFragment();
                        picker.show(getFragmentManager(), "datePicker");

                    }
                }
        );
        //llamada TimePickerDialog

        hora_editext.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if (selectedMinute<10) {
                            hora_editext.setText(selectedHour + ":0" + selectedMinute);
                        } else {
                            hora_editext.setText(selectedHour + ":" + selectedMinute);
                        }
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Seleccione hora");
                mTimePicker.show();

            }
        });

        /*fechaIngInput.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogFragment picker = new DatePickerFragment();
                        picker.show(getFragmentManager(), "datePicker1");

                    }
                }
        );*/

        return v;
    }

    private void setDefaultFields() {
        if(!InsertDesaActivity.radicado.isEmpty() && InsertDesaActivity.radicado != null ){
            idRadicadoInput.setText(InsertDesaActivity.radicado);
        }
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
                if (!camposVacios())
                    guardarDesaEvento();
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

    /**
     * Guarda los cambios de un DesaEvento editado.
     * <p>
     * Si está en modo inserción, entonces crea un nuevo
     * DesaEvento en la base de datos
     */
    public void guardarDesaEvento() {

        //Obtener fecha y hora actual
        java.util.Date fechaActual=new java.util.Date();
        DateFormat formatofechas = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String fechaIng= (formatofechas.format(fechaActual));

        // Obtener valores actuales de los controles
        final String idRadicado = idRadicadoInput.getText().toString();
        final String subEvento = subEventoInput.getSelectedItem().toString();
        final String observaciones = observacionesInput.getText().toString();
        final String fecha = fechaInput.getText().toString();
        final String hora = hora_editext.getText().toString();
        //final String fechaIng = fechaIngInput.getText().toString();

        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("IdRadicado", idRadicado);
        map.put("FECHA", fecha+" "+hora);
        map.put("COD_EVENTO", InsertDesaActivity.cod_evento);
        map.put("SUB_EVENTO", subEvento);
        map.put("OBSERVACIONES", observaciones);
        map.put("USUARIO", userDB.getUser());
        map.put("Estado", "ABIERTO");
        map.put("FechaIngSistema", fechaIng);

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);

        // Depurando objeto Json...
        Log.d(TAG, jobject.toString());

        // Actualizar datos en el servidor
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.POST,
                        Constantes.INSERT,
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
     * Valida si los campos {@link eventoInput}, {@link subEventoInput} y {@link observacionesInput}
     * se han rellenado
     *
     * @return true si alguno o los tres campos están vacios, false si los tres
     * están completos
     */
    public boolean camposVacios() {
        //String subEvento = subEventoInput.getSelectedItem().toString();
        String observaciones = observacionesInput.getText().toString();

        return (observaciones.isEmpty());
    }

    /**
     * Actualiza la fechas de los campos {@link fechaInput}
     * Actualiza la fechas de los campos {@lin fechaIngInput}
     *
     * @param ano Año
     * @param mes Mes
     * @param dia Día
     */
    public void actualizarFecha(int ano, int mes, int dia) {
        // Setear en el textview la fecha
        fechaInput.setText(ano + "-" + (mes + 1) + "-" + dia);
        //fechaIngInput.setText(ano + "-" + (mes + 1) + "-" + dia);
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
