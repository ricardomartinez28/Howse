package com.example.howse.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.howse.R;
import com.example.howse.javabean.DiasTareas;
import com.example.howse.javabean.Tarea;
import com.example.howse.javabean.TareasVisual;

import java.util.ArrayList;

public class AdaptadorTareas extends RecyclerView.Adapter<AdaptadorTareas.TareaViewHolder> {


    Context context;
    ArrayList<TareasVisual> tareas;

    public AdaptadorTareas(Context context, ArrayList<TareasVisual> tareas) {

        this.context=context;
        this.tareas=tareas;

    }

    @NonNull
    @Override
    public TareaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_tareas_dia, parent,false);


        return new TareaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TareaViewHolder holder, int position) {

        TareasVisual tareasVisual=tareas.get(position);



        if(tareasVisual.getProfileImage()==null){
            holder.profile_image.setImageResource(R.mipmap.ic_launcher_round);
        }else{
            Glide.with(holder.username.getContext()).load(tareasVisual.getProfileImage()).into(holder.profile_image);

        }

        holder.username.setText(tareasVisual.getUsername());
        holder.tarea.setText(tareasVisual.getTarea());
    }

    @Override
    public int getItemCount() {
        return tareas.size();
    }

    public class TareaViewHolder extends RecyclerView.ViewHolder{


        ImageView profile_image;
        TextView username;
        TextView tarea;

        public TareaViewHolder(View itemView) {
            super(itemView);

            profile_image= itemView.findViewById(R.id.profileImage);
            username= itemView.findViewById(R.id.username);
            tarea=itemView.findViewById(R.id.tarea);




        }
    }



}
