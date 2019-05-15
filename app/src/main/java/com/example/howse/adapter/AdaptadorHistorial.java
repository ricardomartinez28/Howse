package com.example.howse.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.howse.R;
import com.example.howse.javabean.HistorialCompra;

import java.util.List;

public class AdaptadorHistorial extends RecyclerView.Adapter<AdaptadorHistorial.HistrorialViewHolder> {

    List<HistorialCompra> hLista;

    public AdaptadorHistorial(List<HistorialCompra> hLista) {
        this.hLista = hLista;
    }

    @NonNull
    @Override
    public HistrorialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_historial,parent,false);

        return new AdaptadorHistorial.HistrorialViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HistrorialViewHolder holder, int position) {

        final HistorialCompra hcompra= hLista.get(position);

        holder.tvFecha.setText(hcompra.getFecha());
        holder.tvLista.setText(hcompra.getCompra());

    }

    @Override
    public int getItemCount() {
        return hLista.size();
    }

    public static class HistrorialViewHolder extends RecyclerView.ViewHolder{

        public TextView tvFecha;
        public TextView tvLista;

        public HistrorialViewHolder(View itemView){

            super(itemView);

            tvFecha=itemView.findViewById(R.id.tvFechaHistorial);
            tvLista=itemView.findViewById(R.id.tvLista);
        }
    }
}
