package com.example.howse.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.howse.R;

import java.util.List;

public class AdaptadorDiasSemana extends RecyclerView.Adapter<AdaptadorDiasSemana.DiasViewHolder>{




    @NonNull
    @Override
    public DiasViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull DiasViewHolder diasViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
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
