package com.cco.cristiancarlosjohn.ccogestion.UI.Activities;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import com.cco.cristiancarlosjohn.ccogestion.R;
import com.cco.cristiancarlosjohn.ccogestion.Tools.Constantes;
import com.cco.cristiancarlosjohn.ccogestion.UI.Fragments.ConfirmDialogFragment;
import com.cco.cristiancarlosjohn.ccogestion.UI.Fragments.DatePickerFragment;
import com.cco.cristiancarlosjohn.ccogestion.UI.Fragments.UpdateDesaFragment;

public class UpdateDesaActivity extends AppCompatActivity
        implements DatePickerFragment.OnDateSelectedListener,
        ConfirmDialogFragment.ConfirmDialogListener {

    /**
     * Etiqueta del valor extra del dialogo
     */
    private static final String EXTRA_NOMBRE = "NOMBRE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desaeventos);

        String extra = getIntent().getStringExtra(Constantes.EXTRA_ID);

        if (getSupportActionBar() != null)
            getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_done);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.containerDesaeventos, UpdateDesaFragment.createInstance(extra), "UpdateDesaFragment")
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_form, menu);
        return true;
    }

    @Override
    public void onDateSelected(int year, int month, int day) {
        UpdateDesaFragment updateFragment = (UpdateDesaFragment)
                getSupportFragmentManager().findFragmentByTag("UpdateDesaFragment");

        if (updateFragment != null) {
            updateFragment.actualizarFecha(year, month, day);
        }
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        UpdateDesaFragment fragment = (UpdateDesaFragment)
                getSupportFragmentManager().findFragmentByTag("UpdateDesaFragment");

        if (fragment != null) {
            String extra = dialog.getArguments().getString(EXTRA_NOMBRE);
            String msg = getResources().
                    getString(R.string.dialog_delete_msg);

            if (extra.compareTo(msg) == 0) {
                //fragment.eliminarMeta(); // Eliminar la tarea
            } else {
                finish();
            }

        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        UpdateDesaFragment fragment = (UpdateDesaFragment)
                getSupportFragmentManager().findFragmentByTag("UpdateDesaFragment");

        if (fragment != null) {
            // Nada por el momento
        }
    }

}
