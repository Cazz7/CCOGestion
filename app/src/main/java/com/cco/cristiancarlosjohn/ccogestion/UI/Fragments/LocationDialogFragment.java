package com.cco.cristiancarlosjohn.ccogestion.UI.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.cco.cristiancarlosjohn.ccogestion.R;
/**
 * Created by cristian.zapata on 14/09/2017.
 */

public class LocationDialogFragment extends DialogFragment {

    public LocationDialogFragment() {
    }

    public static interface OnCompleteListener {
        public abstract void onComplete(String time);
    }

    private OnCompleteListener mListener;

    public static LocationDialogFragment newInstance(String extra) {
        LocationDialogFragment frag = new LocationDialogFragment();
        return frag;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialog_origen, null))
                // Add action buttons
                .setPositiveButton(R.string.send, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Dialog view = (Dialog) dialog;

                        EditText inputText = (EditText) view.findViewById(R.id.etUbicacion);
                        String input;
                        input = inputText.getText().toString();
                        if ( !(input.isEmpty()) || input != null ){
                            mListener.onComplete(input);
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        LocationDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.mListener = (OnCompleteListener)context;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnCompleteListener");
        }
    }
}
