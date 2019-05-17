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
import com.example.howse.ListaCompraActivity;
import com.example.howse.R;
import com.example.howse.javabean.Articulo;
import com.example.howse.javabean.Usuario;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


public class CardCompraAdapter extends RecyclerView.Adapter<CardCompraAdapter.CartaCompraViewHolder>{



    List<Articulo> mLista;
    DatabaseReference reference;
    private Context context;



    public CardCompraAdapter( List<Articulo> mLista) {

        this.mLista = mLista;
        reference= FirebaseDatabase.getInstance().getReference("Articulos");
    }

    @NonNull
    @Override
    public CartaCompraViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_compra,parent, false);

        context=parent.getContext();
        return new CardCompraAdapter.CartaCompraViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CartaCompraViewHolder holder, final int position) {

        final Articulo articulo=mLista.get(position);

        holder.tvArticulo.setText(articulo.getNombre());
        if(articulo.getPrecio().trim().equals("")){
            holder.tvPrecio.setText("Precio : No especificado");

        }else {

            holder.tvPrecio.setText("Precio Aprox: "+articulo.getPrecio());
            holder.username.setText(articulo.getUsuario().getNombreUsuario());

        }

        if(articulo.getUsuario().getFotoUsuario()==null){
            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        }else{
            Glide.with(holder.username.getContext()).load(articulo.getUsuario().getFotoUsuario()).into(holder.profile_image);

        }





        holder.btnEl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder= new AlertDialog.Builder(context);

                builder.setMessage("Seguro que quieres eliminar este elemento?")
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                mLista.remove(mLista.get(position).getKeyArt());
                                reference.child(mLista.get(position).getKeyArt()).removeValue();
                                removeAt(position);

                            }
                        }).setNegativeButton("Cancelar", null);


                AlertDialog alert=builder.create();
                alert.show();





            }
        });

    }

    @Override
    public int getItemCount() {

        //System.out.println("La lissssssssssstaaaaaaaaaa eeessssssssss asiiiii de laaargaaaaa"+mLista.size());
        return mLista.size();

    }

    public static  class CartaCompraViewHolder extends RecyclerView.ViewHolder{

        public TextView tvArticulo;
        public TextView tvPrecio;
        public TextView username;
        public ImageView profile_image;
        public ImageButton btnEl;


       public CartaCompraViewHolder(View itemView){

           super(itemView);
           tvArticulo=itemView.findViewById(R.id.tvArticulo);
           tvPrecio=itemView.findViewById(R.id.tvPrecio);
           username=itemView.findViewById(R.id.username);
           profile_image=itemView.findViewById(R.id.profileImage);
           btnEl=itemView.findViewById(R.id.btnEliminarArt);



       }
    }

    /*
             public void removeAt(int position) {
        datos.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, datos.size());
    }
         */

    public void removeAt(int position){
        mLista.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mLista.size());
    }
}
