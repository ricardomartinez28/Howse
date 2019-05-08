package com.example.howse.javabean;

import java.io.Serializable;
import java.util.ArrayList;

public class Tabla implements Serializable {

    private ArrayList<Tarea> tarea;
    private String codCasa;

    public Tabla() {
    }

    public Tabla(ArrayList<Tarea> tarea, String codCasa) {
        this.tarea = tarea;
        this.codCasa = codCasa;
    }

    public ArrayList<Tarea> getTarea() {
        return tarea;
    }

    public void setTarea(ArrayList<Tarea> tarea) {
        this.tarea = tarea;
    }

    public String getCodCasa() {
        return codCasa;
    }

    public void setCodCasa(String codCasa) {
        this.codCasa = codCasa;
    }


}


