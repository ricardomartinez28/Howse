package com.example.howse.javabean;

import java.util.ArrayList;

public class DiasTareas {

    String dia;
    ArrayList<TareasVisual> listaTareas;


    public DiasTareas(String dia, ArrayList<TareasVisual> listaTareas) {
        this.dia = dia;
        this.listaTareas = listaTareas;
    }

    public DiasTareas() {
    }

    public String getDia() {
        return dia;
    }

    public ArrayList<TareasVisual> getListaTareas() {
        return listaTareas;
    }
}
