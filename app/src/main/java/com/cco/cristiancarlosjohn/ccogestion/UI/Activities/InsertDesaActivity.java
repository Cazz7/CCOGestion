package com.cco.cristiancarlosjohn.ccogestion.UI.Activities;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.cco.cristiancarlosjohn.ccogestion.R;
import com.cco.cristiancarlosjohn.ccogestion.UI.Fragments.ConfirmDialogFragment;
import com.cco.cristiancarlosjohn.ccogestion.UI.Fragments.DatePickerFragment;
import com.cco.cristiancarlosjohn.ccogestion.UI.Fragments.InsertDesaFragment;

public class InsertDesaActivity extends AppCompatActivity
        implements DatePickerFragment.OnDateSelectedListener,
        ConfirmDialogFragment.ConfirmDialogListener {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_desaeventos);

            if (getSupportActionBar() != null)
                getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_done);

            Toast.makeText(this, "Estoy en el ACTIVITY INSERT", Toast.LENGTH_SHORT).show();

            // Creación del fragmento de inserción
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.containerDesaeventos, new InsertDesaFragment(), "InsertDesaFragment")
                        .commit();
            }
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu_form, menu);
            return true;
        }

        @Override
        public void onDateSelected(int year, int month, int day) {
            InsertDesaFragment insertFragment = (InsertDesaFragment)
                    getSupportFragmentManager().findFragmentByTag("InsertDesaFragment");

            if (insertFragment != null) {
                insertFragment.actualizarFecha(year, month, day);
            }
        }

        @Override
        public void onDialogPositiveClick(DialogFragment dialog) {
            InsertDesaFragment insertFragment = (InsertDesaFragment)
                    getSupportFragmentManager().findFragmentByTag("InsertDesaFragment");

            if (insertFragment != null) {
                finish(); // Finalizar actividad descartando cambios
            }
        }

        @Override
        public void onDialogNegativeClick(DialogFragment dialog) {
            InsertDesaFragment insertFragment = (InsertDesaFragment)
                    getSupportFragmentManager().findFragmentByTag("InsertDesaFragment");

            if (insertFragment != null) {
                //TODO: Nada por el momento
            }
        }

    }
