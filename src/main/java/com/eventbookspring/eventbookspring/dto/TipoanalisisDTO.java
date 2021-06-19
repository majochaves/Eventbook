package com.eventbookspring.eventbookspring.dto;

import com.eventbookspring.eventbookspring.entity.Analisis;

import java.util.List;

public class TipoanalisisDTO {

    private Integer id;
    private String nombre;
//    private Analisis analisisId;
    private List<CampoanalisisDTO> campoanalisisList;

    public TipoanalisisDTO(String nombre, List<CampoanalisisDTO> campoanalisisList){
        this.nombre = nombre;
        this.campoanalisisList = campoanalisisList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

//    public Analisis getAnalisisId() {
//        return analisisId;
//    }
//
//    public void setAnalisisId(Analisis analisisId) {
//        this.analisisId = analisisId;
//    }

    public List<CampoanalisisDTO> getCampoanalisisList() {
        return campoanalisisList;
    }

    public void setCampoanalisisList(List<CampoanalisisDTO> campoanalisisList) {
        this.campoanalisisList = campoanalisisList;
    }
}
