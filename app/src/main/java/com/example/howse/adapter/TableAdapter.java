package com.example.howse.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.howse.TablaActivity;
import com.example.howse.javabean.Tarea;
import com.example.howse.R;


import java.util.ArrayList;


public class TableAdapter extends RecyclerView.Adapter<TableAdapter.TablaViewHolder> {
    private ArrayList<String> dias;
    private TablaActivity activity_tabla;



    public TableAdapter(ArrayList<String> dias, TablaActivity activity_tabla) {
        this.dias = dias;
        this.activity_tabla = activity_tabla;
    }

    @Override
    public TablaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from( viewGroup.getContext()).inflate( R.layout.cardview,viewGroup,false );
        TablaViewHolder tvh=new TablaViewHolder(v);
        return tvh;
    }

    @Override
    public void onBindViewHolder(@NonNull TablaViewHolder TablaViewHolder, int posicion) {
        final String tar=dias.get(posicion);
        TablaViewHolder.diaSemana.setText( tar );


    }

    @Override
    public int getItemCount() {
        return dias.size();
    }
    public void clear() {
        dias.clear();
    }

    public class TablaViewHolder extends RecyclerView.ViewHolder{
        private Context context;

        private TextView diaSemana,tvtv;

        public TablaViewHolder(View itemView){
            super(itemView);
            diaSemana=itemView.findViewById( R.id.tvDia );

        }
        public void bindViaje(Tarea tr) {
            //diaSemana.setText(String.format(context.getString(R.string.tvDia), tr.getDiaSemana()));
           // tvDescripcion.setText(String.format(context.getString(R.string.tvvDescripcion1), tr.getDescripcion()));


        }

    }
}