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
import com.example.howse.javabean.Articulo;
import com.example.howse.javabean.Usuario;

import java.util.List;


public class CardCompraAdapter extends RecyclerView.Adapter<CardCompraAdapter.CartaCompraViewHolder>{



    List<Articulo> mLista;
    ViewGroup viewGroup;

    public CardCompraAdapter( List<Articulo> mLista) {

        this.mLista = mLista;
    }

    @NonNull
    @Override
    public CartaCompraViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_compra,parent, false);

        return new CardCompraAdapter.CartaCompraViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartaCompraViewHolder holder, int position) {

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
    }

    @Override
    public int getItemCount() {

        System.out.println("La lissssssssssstaaaaaaaaaa eeessssssssss asiiiii de laaargaaaaa"+mLista.size());
        return mLista.size();

    }

    public static  class CartaCompraViewHolder extends RecyclerView.ViewHolder{

        public TextView tvArticulo;
        public TextView tvPrecio;
        public TextView username;
        public ImageView profile_image;

       public CartaCompraViewHolder(View itemView){

           super(itemView);
           tvArticulo=itemView.findViewById(R.id.tvArticulo);
           tvPrecio=itemView.findViewById(R.id.tvPrecio);
           username=itemView.findViewById(R.id.username);
           profile_image=itemView.findViewById(R.id.profileImage);
       }
    }
}
