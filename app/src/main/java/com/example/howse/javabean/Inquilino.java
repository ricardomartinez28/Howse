package com.example.howse.javabean;

import java.io.Serializable;

public class Inquilino implements Serializable{
    public String keyUsuario;
    public String nombreUsuario;
    public String apellidosUsuario;
    public String emailUsuario;
    public String fotoUsuario;
    public String codCasa;

    public Inquilino(String keyUsuario, String nombreUsuario, String apellidosUsuario, String emailUsuario, String fotoUsuario, String codCasa) {
        this.keyUsuario = keyUsuario;
        this.nombreUsuario = nombreUsuario;
        this.apellidosUsuario = apellidosUsuario;
        this.emailUsuario = emailUsuario;
        this.fotoUsuario = fotoUsuario;
        this.codCasa = codCasa;
    }

    public Inquilino() {
    }

    public Inquilino(String nombreUsuario, String apellidosUsuario, String emailUsuario, String fotoUsuario, String codCasa) {
        this.nombreUsuario = nombreUsuario;
        this.apellidosUsuario = apellidosUsuario;
        this.emailUsuario = emailUsuario;
        this.fotoUsuario = fotoUsuario;
        this.codCasa = codCasa;
    }

    @Override
    public String toString() {
        return "Inquilino{" +
                "keyUsuario='" + keyUsuario + '\'' +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", apellidosUsuario='" + apellidosUsuario + '\'' +
                ", emailUsuario='" + emailUsuario + '\'' +
                ", fotoUsuario='" + fotoUsuario + '\'' +
                ", codCasa='" + codCasa + '\'' +
                '}';
    }

    public String getKeyUsuario() {
        return keyUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getApellidosUsuario() {
        return apellidosUsuario;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public String getFotoUsuario() {
        return fotoUsuario;
    }

    public String getCodCasa() {
        return codCasa;
    }
}
