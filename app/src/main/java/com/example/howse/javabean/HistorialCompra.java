package com.example.howse.javabean;

public class HistorialCompra {

    String fecha;
    String compra;
    String codCasa;

    public HistorialCompra() {
    }

    public HistorialCompra(String compra, String codCasa,  String fecha) {
        this.compra = compra;
        this.codCasa = codCasa;
        this.fecha=fecha;
    }


    public String getCompra() {
        return compra;
    }

    public String getCodCasa() {
        return codCasa;
    }
}
