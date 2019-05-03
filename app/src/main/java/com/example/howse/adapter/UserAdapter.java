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
import com.example.howse.javabean.Usuario;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{

    private Context mContext;
    private List<Usuario> mUsers;


    public UserAdapter(Context mContext, List<Usuario> mUsers) {
        this.mContext = mContext;
        this.mUsers = mUsers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.usuario_item, parent, false);

        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Usuario usuario=mUsers.get(position);
        holder.username.setText(usuario.getNombreUsuario());
        if(usuario.getFotoUsuario()==null){
            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        }else{
            Glide.with(mContext).load(usuario.getFotoUsuario()).into(holder.profile_image);
        }

    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

     public TextView username;
     public ImageView profile_image;

     public ViewHolder(View itemView){
         super(itemView);

         username= itemView.findViewById(R.id.username);
         profile_image=itemView.findViewById(R.id.profileImage);
     }

    }
}
