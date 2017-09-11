package com.cco.cristiancarlosjohn.ccogestion.UI.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cco.cristiancarlosjohn.ccogestion.Model.Radicados;
import com.cco.cristiancarlosjohn.ccogestion.R;

import java.util.List;

/**
 * Adaptador del recycler view
 */
public class RadicadosAdapter extends RecyclerView.Adapter<RadicadosAdapter.MetaViewHolder>
        implements ItemClickListener {

    private List<Radicados> items;
    private Context context;


    public RadicadosAdapter(List<Radicados> items, Context context) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public MetaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_list, viewGroup, false);
        return new MetaViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(MetaViewHolder viewHolder, int i) {
        viewHolder.titulo.setText(items.get(i).getVia());
        viewHolder.prioridad.setText(items.get(i).getFecha_Creacion());
        viewHolder.fechaLim.setText(items.get(i).getIdRadicado());
        viewHolder.categoria.setText(items.get(i).getCod_Evento());
    }

    /**
     * Sobrescritura del método de la interfaz {@link ItemClickListener}
     *
     * @param view     item actual
     * @param position posición del item actual
     */
    @Override
    public void onItemClick(View view, int position) {
         Toast.makeText(
                 view.getContext(),
                 items.get(position).getIdRadicado(),
                Toast.LENGTH_LONG).show();
        /*DetailActivity.launch(
                (Activity) context, items.get(position).getIdMeta());*/
    }





    public static class MetaViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        // Campos respectivos de un item
        public TextView titulo;
        public TextView prioridad;
        public TextView fechaLim;
        public TextView categoria;
        public ItemClickListener listener;

        public MetaViewHolder(View v, ItemClickListener listener) {
            super(v);
            titulo = (TextView) v.findViewById(R.id.titulo);
            prioridad = (TextView) v.findViewById(R.id.prioridad);
            fechaLim = (TextView) v.findViewById(R.id.fecha);
            categoria = (TextView) v.findViewById(R.id.categoria);
            this.listener = listener;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, getAdapterPosition());
        }
    }
}


interface ItemClickListener {
    void onItemClick(View view, int position);
}
