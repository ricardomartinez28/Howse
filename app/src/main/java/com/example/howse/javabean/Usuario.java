package com.example.howse.javabean;

import java.io.Serializable;

public class Usuario implements Serializable {

    private String keyUsuario;
    private String nombreUsuario;
    private String apellidosUsuario;
    private String emailUsuario;
    private String fotoUsuario;
    private String codCasa;
    private Boolean tipoUs;


    public Usuario(String keyUsuario, String nombreUsuario, String apellidosUsuario, String emailUsuario, String fotoUsuario, String codCasa, Boolean tipoUs) {
        this.keyUsuario = keyUsuario;
        this.nombreUsuario = nombreUsuario;
        this.apellidosUsuario = apellidosUsuario;
        this.emailUsuario = emailUsuario;
        this.fotoUsuario = fotoUsuario;
        this.codCasa = codCasa;
        this.tipoUs = tipoUs;
    }



    public Usuario(String nombreUsuario, String apellidosUsuario, String emailUsuario, String fotoUsuario, String codCasa, Boolean tipoUs) {
        this.nombreUsuario = nombreUsuario;
        this.apellidosUsuario = apellidosUsuario;
        this.emailUsuario = emailUsuario;
        this.fotoUsuario = fotoUsuario;
        this.codCasa = codCasa;
        this.tipoUs = tipoUs;
    }

    public Usuario() {
    }




    public void setKeyUsuario(String keyUsuario) {
        this.keyUsuario = keyUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setApellidosUsuario(String apellidosUsuario) {
        this.apellidosUsuario = apellidosUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public void setFotoUsuario(String fotoUsuario) {
        this.fotoUsuario = fotoUsuario;
    }

    public String getKeyUsuario() {
        return keyUsuario;
    }

    public String getCodCasa() {
        return codCasa;
    }

    public Boolean getTipoUs() {
        return tipoUs;
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

    public void setTipoUs(Boolean tipoUs) {
        this.tipoUs = tipoUs;
    }

    public void setCodCasa(String codCasa) {
        this.codCasa = codCasa;
    }


}
