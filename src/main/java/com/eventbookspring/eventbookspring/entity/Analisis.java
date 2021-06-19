/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventbookspring.eventbookspring.entity;

import com.eventbookspring.eventbookspring.dto.AnalisisDTO;

import java.io.Serializable;
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
@Table(name = "ANALISIS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Analisis.findAll", query = "SELECT a FROM Analisis a")
    , @NamedQuery(name = "Analisis.findById", query = "SELECT a FROM Analisis a WHERE a.id = :id")
    , @NamedQuery(name = "Analisis.findByDescripcion", query = "SELECT a FROM Analisis a WHERE a.descripcion = :descripcion")})
public class Analisis implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "DESCRIPCION", length = 300, nullable = false)
    private String descripcion;
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "analisisId", fetch = FetchType.LAZY)
    private List<Tipoanalisis> tipoanalisisList;
    @JoinColumn(name = "ANALISTA_USUARIO_ID", referencedColumnName = "USUARIO_ID")
    @ManyToOne(optional = false)  //, cascade = {CascadeType.MERGE}
    private Analista analistaUsuarioId;

    public Analisis() {
    }

    public Analisis(Integer id) {
        this.id = id;
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

    @XmlTransient
    public List<Tipoanalisis> getTipoanalisisList() {
        return tipoanalisisList;
    }

    public void setTipoanalisisList(List<Tipoanalisis> tipoanalisisList) {
        this.tipoanalisisList = tipoanalisisList;
    }

    public Analista getAnalistaUsuarioId() {
        return analistaUsuarioId;
    }

    public void setAnalistaUsuarioId(Analista analistaUsuarioId) {
        this.analistaUsuarioId = analistaUsuarioId;
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
        if (!(object instanceof Analisis)) {
            return false;
        }
        Analisis other = (Analisis) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Analisis[ id=" + id + " ]";
    }

    @Transient
    public AnalisisDTO getAnalisisDtoLazy(){
        AnalisisDTO analisisDTO = new AnalisisDTO();
        analisisDTO.setAnalistaUsuarioId(this.analistaUsuarioId);
        analisisDTO.setDescripcion(this.descripcion);
        analisisDTO.setId(this.id);
        analisisDTO.setTipoanalisisList(null);
        return analisisDTO;
    }

}
