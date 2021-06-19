/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventbookspring.eventbookspring.entity;

import com.eventbookspring.eventbookspring.dto.CampoanalisisDTO;
import com.eventbookspring.eventbookspring.dto.TipoanalisisDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
//import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author memoriasIT
 */
@Entity
@Table(name = "TIPOANALISIS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipoanalisis.findAll", query = "SELECT t FROM Tipoanalisis t")
    , @NamedQuery(name = "Tipoanalisis.findById", query = "SELECT t FROM Tipoanalisis t WHERE t.id = :id")
    , @NamedQuery(name = "Tipoanalisis.findByNombre", query = "SELECT t FROM Tipoanalisis t WHERE t.nombre = :nombre")})
public class Tipoanalisis implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "NOMBRE", length = 50)
    private String nombre;
    @JoinColumn(name = "ANALISIS_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false) //, cascade = {CascadeType.MERGE}
    private Analisis analisisId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoanalisis")
    private List<Campoanalisis> campoanalisisList;

    public Tipoanalisis() {
    }

    public Tipoanalisis(Integer id) {
        this.id = id;
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

    public Analisis getAnalisisId() {
        return analisisId;
    }

    public void setAnalisisId(Analisis analisisId) {
        this.analisisId = analisisId;
    }

    @XmlTransient
    public List<Campoanalisis> getCampoanalisisList() {
        return campoanalisisList;
    }

    public void setCampoanalisisList(List<Campoanalisis> campoanalisisList) {
        this.campoanalisisList = campoanalisisList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipoanalisis)) {
            return false;
        }
        Tipoanalisis other = (Tipoanalisis) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Tipoanalisis[ id=" + id + " ]";
    }

    @Transient
    public TipoanalisisDTO getTipoanalisisDto(){
        TipoanalisisDTO thisTipoanalisisDto = new TipoanalisisDTO();
        thisTipoanalisisDto.setNombre(this.nombre);
        thisTipoanalisisDto.setId(this.id);
        List<CampoanalisisDTO> listaCampoanalisisDto = new ArrayList<>();
        for(Campoanalisis ca : this.campoanalisisList){
            listaCampoanalisisDto.add(ca.getCampoanalisisDto());
        }
        thisTipoanalisisDto.setCampoanalisisList(listaCampoanalisisDto);
        return thisTipoanalisisDto;
    }
    
}
