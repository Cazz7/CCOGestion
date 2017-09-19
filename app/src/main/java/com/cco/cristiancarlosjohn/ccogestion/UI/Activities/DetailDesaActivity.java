package com.cco.cristiancarlosjohn.ccogestion.UI.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.cco.cristiancarlosjohn.ccogestion.R;
import com.cco.cristiancarlosjohn.ccogestion.Tools.Constantes;
import com.cco.cristiancarlosjohn.ccogestion.UI.Fragments.DetailDesaFragment;

public class DetailDesaActivity extends AppCompatActivity {

    /**
     * Instancia global del DesaEvento a detallar
     */
    private String IdDesaeventos;

    /**
     * Inicia una nueva instancia de la actividad
     *
     * @param activity Contexto desde donde se lanzará
     * @param IdDesaeventos   Identificador del DesaEvento a detallar
     */


    public static void launch(Activity activity, String IdDesaeventos) {
        Intent intent = getLaunchIntent(activity, IdDesaeventos);
        activity.startActivityForResult(intent, Constantes.CODIGO_DETALLE);
    }

    /**
     * Construye un Intent a partir del contexto y la actividad
     * de detalle.
     *
     * @param context Contexto donde se inicia
     * @param IdDesaeventos  Identificador del DesaEvento
     * @return Intent listo para usar
     */

    public static Intent getLaunchIntent(Context context, String IdDesaeventos) {
        Intent intent = new Intent(context, DetailDesaActivity.class);
        intent.putExtra(Constantes.EXTRA_ID, IdDesaeventos);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desaeventos);

        if (getSupportActionBar() != null) {
            // Dehabilitar titulo de la actividad
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            // Setear ícono "X" como Up button
            getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_close);
        }


        // Retener instancia
        if (getIntent().getStringExtra(Constantes.EXTRA_ID) != null)
            IdDesaeventos = getIntent().getStringExtra(Constantes.EXTRA_ID);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.containerDesaeventos, DetailDesaFragment.createInstance(IdDesaeventos), "DetailDesaFragment")
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constantes.CODIGO_ACTUALIZACION) {
            if (resultCode == RESULT_OK) {
                DetailDesaFragment fragment = (DetailDesaFragment) getSupportFragmentManager().
                        findFragmentByTag("DetailDesaFragment");
                fragment.cargarDatos();

                setResult(RESULT_OK); // Propagar código para actualizar
            } else if (resultCode == 203) {
                setResult(203);
                finish();
            } else {
                setResult(RESULT_CANCELED);
            }
        }
    }

}
