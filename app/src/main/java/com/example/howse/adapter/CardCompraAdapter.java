package com.example.howse.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.howse.R;
import com.example.howse.javabean.Articulo;
import com.example.howse.javabean.Usuario;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


public class CardCompraAdapter extends RecyclerView.Adapter<CardCompraAdapter.CartaCompraViewHolder>{



    List<Articulo> mLista;
    DatabaseReference reference;


    public CardCompraAdapter( List<Articulo> mLista) {

        this.mLista = mLista;
        reference= FirebaseDatabase.getInstance().getReference("Articulos");
    }

    @NonNull
    @Override
    public CartaCompraViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_compra,parent, false);

        return new CardCompraAdapter.CartaCompraViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CartaCompraViewHolder holder, final int position) {

        final Articulo articulo=mLista.get(position);

        holder.tvArticulo.setText(articulo.getNombre());
        if(articulo.getPrecio().trim().equals("")){
            holder.tvPrecio.setText("Precio: No especificado");

        }
        holder.tvPrecio.setText("Precio:"+articulo.getPrecio());
        holder.username.setText(articulo.getUsuario().getNombreUsuario());

        if(articulo.getUsuario().getFotoUsuario()==null){
            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        }else{
            Glide.with(holder.username.getContext()).load(articulo.getUsuario().getFotoUsuario()).into(holder.profile_image);

        }
        /*
         tbFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Quitar de los favoritos de usuario
                    user[0].getPlayasUsuarioFav().remove(playa.getId());
                    dbR.child(user[0].getKeyUsuario()).setValue(user[0]);
                    adap.removeAt(getAdapterPosition());
                }
            });


         */
        holder.btnEl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLista.remove(mLista.get(position).getKeyArt());
                reference.child(mLista.get(position).getKeyArt()).removeValue();
                removeAt(position);

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
        public Button btnEl;

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
