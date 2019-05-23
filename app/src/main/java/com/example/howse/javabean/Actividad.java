package com.example.howse.javabean;

public class Actividad {
    private String nombre;
    private String codCasa;

    public Actividad(String nombre, String codCasa) {
        this.nombre = nombre;
        this.codCasa = codCasa;
    }

    public Actividad() {
    }

    public String getNombre() {
        return nombre;
    }



    public String getCodCasa() {
        return codCasa;
    }

    public void setCodCasa(String codCasa) {
        this.codCasa = codCasa;
    }
}