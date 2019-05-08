package com.example.howse.javabean;

import java.io.Serializable;

public class Tarea implements Serializable {

    private String persona;
    private String tipoTarea;
    private String diaSemana;

    public Tarea() {
    }

    public Tarea(String persona, String tipoTarea, String diaSemana) {
        this.persona = persona;
        this.tipoTarea = tipoTarea;
        this.diaSemana = diaSemana;
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

