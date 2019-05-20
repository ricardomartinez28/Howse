package com.example.howse.javabean;

import android.widget.ImageView;

public class TareasVisual {


    private String fotoUsuario;;
    String username;
    String Tarea;


    public TareasVisual(String fotoUsuario, String username, String tarea) {
        this.fotoUsuario = fotoUsuario;
        this.username = username;
        Tarea = tarea;
    }

    public String getProfileImage() {
        return fotoUsuario;
    }

    public String getUsername() {
        return username;
    }

    public String getTarea() {
        return Tarea;
    }
}
