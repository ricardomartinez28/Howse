package com.example.howse.javabean;

import java.io.Serializable;

public class Tarea implements Serializable {

    private  String keyTarea;
    private Usuario persona;
    private String tipoTarea;
    private String diaSemana;
    private String codigoCasa;

    public Tarea() {
    }

    public Tarea(String keyTarea, Usuario persona, String tipoTarea, String diaSemana, String codigoCasa) {
        this.keyTarea=keyTarea;
        this.persona = persona;
        this.tipoTarea = tipoTarea;
        this.diaSemana = diaSemana;
        this.codigoCasa = codigoCasa;
    }

    public String getCodigoCasa() {
        return codigoCasa;
    }

    public void setCodigoCasa(String codigoCasa) {
        this.codigoCasa = codigoCasa;
    }

    public Usuario getPersona() {
        return persona;
    }

    public String getTipoTarea() {
        return tipoTarea;
    }

    public void setTipoTarea(String tipoTarea) {
        this.tipoTarea = tipoTarea;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public String getKeyTarea() {
        return keyTarea;
    }
}