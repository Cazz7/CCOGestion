package com.cco.cristiancarlosjohn.ccogestion.UI.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cco.cristiancarlosjohn.ccogestion.R;
import com.cco.cristiancarlosjohn.ccogestion.Tools.Constantes;
import com.cco.cristiancarlosjohn.ccogestion.Tools.DataBaseHelper.UserDBHelper;
import com.cco.cristiancarlosjohn.ccogestion.UI.Fragments.LocationDialogFragment;
import com.cco.cristiancarlosjohn.ccogestion.WEB.VolleySingleton;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.cco.cristiancarlosjohn.ccogestion.R.id.fab;

public class ConfirmActivity extends AppCompatActivity implements LocationDialogFragment.OnCompleteListener{

    public String Respuestas_Sito_Enviar;

    UserDBHelper dbUsers;
    String accion; //Acción que realiza el usuario al dar clic
    String radicado;
    String codigo;
    String via;
    String sector;

    //Componentes UI
    Toolbar toolbar;
    TextView tvRadicado, tvCodigo, tvVia, tvSector;
    Button bDetalle, bAceptar, bLlegar, bSuperar;
    FloatingActionButton fabHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        bindUI();

        setSupportActionBar(toolbar);
        dbUsers = new UserDBHelper(this);

        readNotification();

        fabHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void bindUI() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvRadicado = (TextView)findViewById(R.id.tvRadicado);
        tvCodigo = (TextView)findViewById(R.id.tvCodigo);
        tvVia = (TextView)findViewById(R.id.tvVia);
        tvSector = (TextView)findViewById(R.id.tvSector);
        bDetalle = (Button)findViewById(R.id.btnDetalleEvento);
        bAceptar = (Button)findViewById(R.id.btnAceptarEvento);
        bLlegar = (Button)findViewById(R.id.btnLlegarEvento);
        bSuperar = (Button)findViewById(R.id.btnSuperarEvento);
        fabHome = (FloatingActionButton) findViewById(fab);
    }

    public static void launch(Activity activity, String idradicado, String cod_evento, String via, String kilo_sector) {
        Intent intent = getLaunchIntent(activity, idradicado, cod_evento, via, kilo_sector);
        activity.startActivity(intent);
    }


    public static Intent getLaunchIntent(Context context, String idradicado, String cod_evento, String via, String kilo_sector) {
        Intent i = new Intent(context, ConfirmActivity.class);
        i.putExtra(Constantes.RADICADO, idradicado);
        i.putExtra(Constantes.COD_EVENTO, cod_evento);
        i.putExtra(Constantes.VIA, via);
        i.putExtra(Constantes.SECTOR, kilo_sector);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return i;
    }

    private void readNotification() {
        Intent intent = getIntent();
        radicado = intent.getStringExtra(Constantes.RADICADO);
        codigo = intent.getStringExtra(Constantes.COD_EVENTO);
        via = intent.getStringExtra(Constantes.VIA);
        sector = intent.getStringExtra(Constantes.SECTOR);

        tvRadicado.setText(" " + radicado);
        tvCodigo.setText(" " + codigo);
        tvVia.setText(" " + via);
        tvSector.setText(" " + sector);
    }

    public void onAction(View v)
    {
        switch ( v.getId() ){
            case R.id.btnAceptarEvento:
                accion = getResources().getString(R.string.accion_aceptar);
                DisplayLocationDialog();
                break;
            case R.id.btnLlegarEvento:
                accion = getResources().getString(R.string.accion_llegar);
                RespuestaSitio();
                break;
            case R.id.btnSuperarEvento:
                accion = getResources().getString(R.string.accion_disponible);
                DisplayLocationDialog();
                break;
            default:
                break;
        }


    }

    private void RespuestaSitio() {
        // where we will store or remove selected items
        final ArrayList<Integer> mSelectedItems = new ArrayList<Integer>();
        //Obtengo String del array unidades_operacion
        final String[] Respuesta_Sitio_String = getResources().getStringArray(R.array.Respuestas_Sitio_Array);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // set the dialog title
        builder.setTitle("Seleccione en sitio")
                // specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive call backs when items are selected
                // R.array.choices were set in the resources res/values/strings.xml


                .setMultiChoiceItems(R.array.Respuestas_Sitio_Array, null, new DialogInterface.OnMultiChoiceClickListener() {

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
                        Respuestas_Sito_Enviar= dbUsers.getProfile()+"-"+dbUsers.getUser()+ " informa en sitio:" ;
                        for(Integer i : mSelectedItems){
                            Respuestas_Sito_Enviar +=", "+Respuesta_Sitio_String[i];
                        }
                        Toast.makeText(
                                getApplicationContext(),
                                "Respuestas seleccionadas: " + Respuestas_Sito_Enviar,
                                Toast.LENGTH_LONG).show();
                        createVolleyRequest(Respuestas_Sito_Enviar);


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

    private void DisplayLocationDialog() {
        showAlertDialog();
    }

    private void showAlertDialog() {
        FragmentManager fm = getSupportFragmentManager();
        LocationDialogFragment alertDialog = LocationDialogFragment.newInstance("Some title");
        alertDialog.show(fm, "fragment_alert");
    }

    @Override
    //Respuesta de la elección
    public void onComplete(String ubicacion) {
        if( !ubicacion.isEmpty() && ubicacion != null){
            String observacion = obtenerObservaciones(accion,ubicacion);
            createVolleyRequest(observacion);
        }
    }

    private void createVolleyRequest(String observacion) {
        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        //Elementos a enviar a la request php
        map.put(Constantes.IDRADICADO, radicado);
        map.put(Constantes.FECHA_CREACION, ObtenerTiempo());
        map.put(Constantes.COD_EVENTO2, codigo);
        map.put(Constantes.SUB_EVENTO, "");
        map.put(Constantes.OBSERVACIONES, observacion);
        map.put(Constantes.ESTADO, "ABIERTO");
        map.put(Constantes.USUARIO, dbUsers.getUser());
        map.put(Constantes.FECHA_ING_SISTEMA, ObtenerTiempo());
        map.put(Constantes.PERFILES_NOTI, "GESTION VIAL," + dbUsers.getProfile());
        map.put(Constantes.ACCION, accion); //TODO: Cambiar el texto de la acción
        map.put(Constantes.UNIDAD, dbUsers.getProfile());
        map.put(Constantes.VIA, via);

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);

        // Depurando objeto Json...
        Log.d("json_confirm", jobject.toString());

        JsonObjectRequest request =  buildRequest(jobject);

        request.setRetryPolicy(new DefaultRetryPolicy(0,-1,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(this).addToRequestQueue(
                request
        );
    }

    private JsonObjectRequest buildRequest(JSONObject jobject) {

        return new JsonObjectRequest(
                Request.Method.POST,
                Constantes.RESPUESTAS_UNIDADES,
                jobject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        procesarRespuesta(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error_volley", "Error Volley: " + error.getMessage());
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
        };
    }

    private String obtenerObservaciones(String accion, String ubicacion) {

        String parte1 = getResources().getString(R.string.observacion_parte1);
        String parte2 = getResources().getString(R.string.observacion_parte2);
        String parte3 = getResources().getString(R.string.observacion_parte3);
        String tarea = accion;

        //Se obtienen los datos del login
        String salida = parte1 + " " +
                        dbUsers.getUser() + " " +
                        dbUsers.getProfile() + " " +
                        parte2 + " " +
                        tarea + " " +
                        parte3 + " " +
                        ubicacion;

        return salida;
    }

    private String ObtenerTiempo() {

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH)+1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);

        return String.valueOf(year) + "-" +
                        String.valueOf(month) + "-" +
                        String.valueOf(day) + " " +
                        String.valueOf(hour) + ":" +
                        String.valueOf(minute) + ":" +
                        String.valueOf(second);
    }

    private void procesarRespuesta(JSONObject response) {

        //Toast.makeText(this, getResources().getString(R.string.not_unidades),Toast.LENGTH_LONG).show();;
    }
}
