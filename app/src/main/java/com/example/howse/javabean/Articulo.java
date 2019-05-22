package com.example.howse.javabean;

import java.io.Serializable;

public class Articulo implements Serializable {


    private String keyArt;
    private String nombre;
    private String precio;
    private Usuario usuario;
    private String codCasa;


    public Articulo(String nombre, String precio, Usuario usuario,String keyArt,String codCasa) {
        this.nombre = nombre;
        this.precio = precio;
        this.usuario = usuario;
        this.keyArt=keyArt;
        this.codCasa=codCasa;

    }

    public Articulo() {
    }

    public String getKeyArt() {
        return keyArt;
    }

    public String getCodCasa() {
        return codCasa;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPrecio() {
        return precio;
    }



    public Usuario getUsuario() {
        return usuario;
    }
}
