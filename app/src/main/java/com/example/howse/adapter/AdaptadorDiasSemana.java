package com.example.howse.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.howse.R;
import com.example.howse.javabean.DiasTareas;
import com.example.howse.javabean.Tarea;
import com.example.howse.javabean.TareasVisual;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorDiasSemana extends RecyclerView.Adapter<AdaptadorDiasSemana.DiasViewHolder>{


    Context context;
    ArrayList<DiasTareas> arrayList;


    public AdaptadorDiasSemana(Context context, ArrayList<DiasTareas> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public DiasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_dias_de_la_semana,parent,false);
        return new DiasViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DiasViewHolder holder, int position) {
        DiasTareas diaTareas= arrayList.get(position);

        String diaSemana= diaTareas.getDia();
        ArrayList<TareasVisual> tareas= diaTareas.getListaTareas();


        holder.tvDia.setText(diaSemana);
        AdaptadorTareas adaptadorTareas= new AdaptadorTareas(context,tareas);

        holder.recyclerView.setHasFixedSize(true);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false));
        holder.recyclerView.setAdapter(adaptadorTareas);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class DiasViewHolder extends RecyclerView.ViewHolder{

        TextView tvDia;
        RecyclerView recyclerView;

        public DiasViewHolder(View itemView){
            super(itemView);

            tvDia=itemView.findViewById(R.id.tvDiaDeLaSemana);
            recyclerView=itemView.findViewById(R.id.rvTareasDia);
        }





    }
}
