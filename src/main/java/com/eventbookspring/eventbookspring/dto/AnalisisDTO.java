package com.eventbookspring.eventbookspring.dto;

import com.eventbookspring.eventbookspring.entity.Analista;

import java.util.List;

public class AnalisisDTO {

    private Integer id;
    private String descripcion;
    private List<TipoanalisisDTO> tipoanalisisList;
    private Analista analistaUsuarioId;

    public AnalisisDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<TipoanalisisDTO> getTipoanalisisList() {
        return tipoanalisisList;
    }

    public void setTipoanalisisList(List<TipoanalisisDTO> tipoanalisisList) {
        this.tipoanalisisList = tipoanalisisList;
    }

    public Analista getAnalistaUsuarioId() {
        return analistaUsuarioId;
    }

    public void setAnalistaUsuarioId(Analista analistaUsuarioId) {
        this.analistaUsuarioId = analistaUsuarioId;
    }
}
