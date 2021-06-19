package com.eventbookspring.eventbookspring.dto;

import com.eventbookspring.eventbookspring.entity.CampoanalisisPK;
import com.eventbookspring.eventbookspring.entity.Tipoanalisis;

import javax.persistence.Basic;
import javax.persistence.Column;


public class CampoanalisisDTO {
    //PKs
    private String nombre;
    private int tipoanalisisId;

    //Valores y/o referencias
    private Double valor;
    //    private TipoanalisisDTO tipoanalisis;


    public CampoanalisisDTO(String nombre, Double valor){
        this.nombre = nombre;
        this.valor = valor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTipoanalisisId() {
        return tipoanalisisId;
    }

    public void setTipoanalisisId(int tipoanalisisId) {
        this.tipoanalisisId = tipoanalisisId;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }



}
