package com.cco.cristiancarlosjohn.ccogestion.UI.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.cco.cristiancarlosjohn.ccogestion.R;
import com.cco.cristiancarlosjohn.ccogestion.Tools.Constantes;
import com.cco.cristiancarlosjohn.ccogestion.UI.Fragments.DesaeventosFragment;

public class DesaeventosActivity extends AppCompatActivity {

    //Componentes UI
    TextView tvRadicado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desaeventos);

        //tvRadicado = (TextView)findViewById(R.id.tvRadicado);

        //readRadicated();

        // Creación del fragmento principal
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.containerDesaeventos, new DesaeventosFragment(), "DesaeventosFragment")
                    .commit();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == Constantes.CODIGO_DETALLE) || (requestCode == 3)) {
            if (resultCode == RESULT_OK || resultCode == 203) {
                DesaeventosFragment fragment = (DesaeventosFragment) getSupportFragmentManager().
                        findFragmentByTag("DesaeventosFragment");
                fragment.cargarAdaptador();
            }
        }
    }

    private void readRadicated() {
        Intent intent = getIntent();
        String radicado = intent.getStringExtra(Constantes.RADICADO);

        //tvRadicado.setText(" " + radicado);
    }

}
