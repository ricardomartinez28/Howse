package com.example.howse.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.howse.R;
import com.example.howse.javabean.DiasTareas;
import com.example.howse.javabean.Tarea;
import com.example.howse.javabean.TareasVisual;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdaptadorTareas extends RecyclerView.Adapter<AdaptadorTareas.TareaViewHolder> {



    Context context;
    ArrayList<TareasVisual> tareas;
    DatabaseReference reference;

    public AdaptadorTareas(Context context, ArrayList<TareasVisual> tareas) {


        this.tareas=tareas;
        reference= FirebaseDatabase.getInstance().getReference("Tareas");

    }

    @NonNull
    @Override
    public TareaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        context=parent.getContext();

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_tareas_dia, parent,false);


        return new TareaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TareaViewHolder holder, final int position) {

        final TareasVisual tareasVisual=tareas.get(position);



        if(tareasVisual.getProfileImage()==null){
            holder.profile_image.setImageResource(R.mipmap.ic_launcher_round);
        }else{
            Glide.with(holder.username.getContext()).load(tareasVisual.getProfileImage()).into(holder.profile_image);

        }

        holder.username.setText(tareasVisual.getUsername());
        holder.tarea.setText(tareasVisual.getUsername()+" tiene que "+tareasVisual.getTarea());

        holder.btnEl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder= new AlertDialog.Builder(context);

                builder.setMessage("Seguro que quieres eliminar este elemento?")
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                tareas.remove(tareas.get(position).getKeyTarea());
                                reference.child(tareas.get(position).getKeyTarea()).removeValue();
                                removeAt(position);
                                notifyDataSetChanged();

                            }
                        }).setNegativeButton("Cancelar", null);


                AlertDialog alert=builder.create();
                alert.show();



            }
        });
    }


    @Override
    public int getItemCount() {
        return tareas.size();
    }

    public class TareaViewHolder extends RecyclerView.ViewHolder{


        ImageView profile_image;
        TextView username;
        TextView tarea;
        ImageButton btnEl;

        public TareaViewHolder(View itemView) {
            super(itemView);

            profile_image= itemView.findViewById(R.id.profileImage);
            username= itemView.findViewById(R.id.username);
            tarea=itemView.findViewById(R.id.tarea);
            btnEl=itemView.findViewById(R.id.btnEliminarTarea);




        }
    }



    public void removeAt(int position){
        tareas.remove(position);
        notifyItemChanged(position);
        notifyItemRangeChanged(position, tareas.size());
    }


}
