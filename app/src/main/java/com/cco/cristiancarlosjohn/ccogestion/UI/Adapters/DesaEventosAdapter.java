package com.cco.cristiancarlosjohn.ccogestion.UI.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cco.cristiancarlosjohn.ccogestion.Model.DesaEventos;
import com.cco.cristiancarlosjohn.ccogestion.R;
import com.cco.cristiancarlosjohn.ccogestion.UI.Activities.DetailDesaActivity;

import java.util.List;

/**
 * Adaptador del recycler view
 */
public class DesaEventosAdapter extends RecyclerView.Adapter<DesaEventosAdapter.DesaEventosViewHolder>
        implements ItemClickListenerDesaEvento {

    /**
     * Lista de objetos {@link DesaEventos} que representan la fuente de datos
     * de inflado
     */
    private List<DesaEventos> items;

    /*
    Contexto donde actua el recycler view
     */
    private Context context;

    public DesaEventosAdapter(List<DesaEventos> items, Context context) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public DesaEventosViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_list_desa, viewGroup, false);
        return new DesaEventosViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(DesaEventosViewHolder viewHolder, int i) {
        viewHolder.evento.setText(items.get(i).getCOD_EVENTO());
        viewHolder.subEvento.setText(items.get(i).getSUB_EVENTO());
        viewHolder.fecha.setText(items.get(i).getFECHA());
        viewHolder.radicado.setText(items.get(i).getIdRadicado());
    }

    /**
     * Sobrescritura del método de la interfaz {@link ItemClickListener}
     *
     * @param view     item actual
     * @param position posición del item actual
     */
    @Override
    public void onItemClick(View view, int position) {
        DetailDesaActivity.launch(
                (Activity) context, items.get(position).getIdDesaeventos());
    }

    public static class DesaEventosViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        // Campos respectivos de un item
        public TextView evento;
        public TextView subEvento;
        public TextView fecha;
        public TextView radicado;
        public ItemClickListenerDesaEvento listener;

        public DesaEventosViewHolder(View v, ItemClickListenerDesaEvento listener) {
            super(v);
            evento = (TextView) v.findViewById(R.id.evento_item);
            subEvento = (TextView) v.findViewById(R.id.subEvento_item);
            fecha = (TextView) v.findViewById(R.id.fecha_item);
            radicado = (TextView) v.findViewById(R.id.radicado_item);
            this.listener = listener;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, getAdapterPosition());
        }

    }
}

interface ItemClickListenerDesaEvento {
    void onItemClick(View view, int position);
}