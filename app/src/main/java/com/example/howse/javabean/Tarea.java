package com.example.howse.javabean;

import java.io.Serializable;

public class Tarea implements Serializable {

    private String persona;
    private String tipoTarea;
    private String diaSemana;
    private String codigoCasa;

    public Tarea() {
    }

    public Tarea(String persona, String tipoTarea, String diaSemana, String codigoCasa) {
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

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
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


}

