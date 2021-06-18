package com.eventbookspring.eventbookspring.clases;

public class Par {
    private String cadena;
    private Long valor;

    public Par(String cadena, Long valor){
        this.cadena = cadena;
        this.valor = valor;
    }

    public String getCadena() {
        return cadena;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }

    public Long getValor() {
        return valor;
    }

    public void setValor(Long valor) {
        this.valor = valor;
    }
}
