/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventbookspring.eventbookspring.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;

/**
 *
 * @author memoriasIT
 */
@Embeddable
public class CampoanalisisPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "NOMBRE", length = 50, nullable = false)
    private String nombre;
    @Basic(optional = false)
    @Column(name = "TIPOANALISIS_ID", nullable = false)
    private int tipoanalisisId;

    public CampoanalisisPK() {
    }

    public CampoanalisisPK(String nombre, int tipoanalisisId) {
        this.nombre = nombre;
        this.tipoanalisisId = tipoanalisisId;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nombre != null ? nombre.hashCode() : 0);
        hash += (int) tipoanalisisId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CampoanalisisPK)) {
            return false;
        }
        CampoanalisisPK other = (CampoanalisisPK) object;
        if ((this.nombre == null && other.nombre != null) || (this.nombre != null && !this.nombre.equals(other.nombre))) {
            return false;
        }
        if (this.tipoanalisisId != other.tipoanalisisId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CampoanalisisPK[ nombre=" + nombre + ", tipoanalisisId=" + tipoanalisisId + " ]";
    }
    
}
