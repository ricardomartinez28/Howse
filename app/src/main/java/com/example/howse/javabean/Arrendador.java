package com.example.howse.javabean;

public class Arrendador {


    private String keyUsuario;
    private String nombreUsuario;
    private String apellidosUsuario;
    private String emailUsuario;
    private String fotoUsuario;
    private String codCasa;

    public Arrendador(String keyUsuario, String nombreUsuario, String apellidosUsuario, String emailUsuario, String fotoUsuario, String codCasa) {
        this.keyUsuario = keyUsuario;
        this.nombreUsuario = nombreUsuario;
        this.apellidosUsuario = apellidosUsuario;
        this.emailUsuario = emailUsuario;
        this.fotoUsuario = fotoUsuario;
        this.codCasa = codCasa;
    }

    public Arrendador(String nombreUsuario, String apellidosUsuario, String emailUsuario, String fotoUsuario, String codCasa) {
        this.nombreUsuario = nombreUsuario;
        this.apellidosUsuario = apellidosUsuario;
        this.emailUsuario = emailUsuario;
        this.fotoUsuario = fotoUsuario;
        this.codCasa = codCasa;
    }

    public Arrendador() {
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
