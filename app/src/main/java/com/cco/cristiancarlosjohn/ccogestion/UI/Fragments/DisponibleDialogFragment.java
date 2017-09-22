package com.cco.cristiancarlosjohn.ccogestion.UI.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.EditText;

import com.cco.cristiancarlosjohn.ccogestion.R;

/**
 * Created by cristian.zapata on 14/09/2017.
 */

public class DisponibleDialogFragment extends DialogFragment {

    public DisponibleDialogFragment() {
    }

    public interface OnCompleteListenerDisponible {
        public abstract void onCompleteDisponible(String time);

    }

    OnCompleteListenerDisponible mListenerD;

    public static DisponibleDialogFragment newInstance(String extra) {
        DisponibleDialogFragment frag = new DisponibleDialogFragment();
        return frag;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialog_disponible, null))
                // Add action buttons
                .setPositiveButton(R.string.send, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Dialog view = (Dialog) dialog;

                        EditText inputText = (EditText) view.findViewById(R.id.Disponible_editext);
                        String input;
                        input = inputText.getText().toString();
                        if ( !(input.isEmpty()) || input != null ){
                            mListenerD.onCompleteDisponible(input);
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DisponibleDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.mListenerD = (OnCompleteListenerDisponible) context;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(context.toString() + " debe implementarse OnCompleteListener");
        }
    }
}
