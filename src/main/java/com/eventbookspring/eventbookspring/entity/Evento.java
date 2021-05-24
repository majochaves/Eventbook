/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventbookspring.eventbookspring.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author memoriasIT
 */
@Entity
@Table(name = "EVENTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Evento.findAll", query = "SELECT e FROM Evento e")
    , @NamedQuery(name = "Evento.findById", query = "SELECT e FROM Evento e WHERE e.id = :id")
    , @NamedQuery(name = "Evento.findByTitulo", query = "SELECT e FROM Evento e WHERE e.titulo = :titulo")
    , @NamedQuery(name = "Evento.findByDescripcion", query = "SELECT e FROM Evento e WHERE e.descripcion = :descripcion")
    , @NamedQuery(name = "Evento.findByFecha", query = "SELECT e FROM Evento e WHERE e.fecha = :fecha")
    , @NamedQuery(name = "Evento.findByFechaLimite", query = "SELECT e FROM Evento e WHERE e.fechaLimite = :fechaLimite")
    , @NamedQuery(name = "Evento.findByCosteEntrada", query = "SELECT e FROM Evento e WHERE e.costeEntrada = :costeEntrada")
    , @NamedQuery(name = "Evento.findByAforo", query = "SELECT e FROM Evento e WHERE e.aforo = :aforo")
    , @NamedQuery(name = "Evento.findByMaxEntradas", query = "SELECT e FROM Evento e WHERE e.maxEntradas = :maxEntradas")
    , @NamedQuery(name = "Evento.findByAsientosFijos", query = "SELECT e FROM Evento e WHERE e.asientosFijos = :asientosFijos")
    , @NamedQuery(name = "Evento.findByNumFilas", query = "SELECT e FROM Evento e WHERE e.numFilas = :numFilas")
    , @NamedQuery(name = "Evento.findByNumAsientosFila", query = "SELECT e FROM Evento e WHERE e.numAsientosFila = :numAsientosFila")})
public class Evento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "TITULO", length = 50, nullable = false)
    private String titulo;
    @Column(name = "DESCRIPCION", length = 200, nullable = false)
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "FECHA")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "FECHA_LIMITE")
    @Temporal(TemporalType.DATE)
    private Date fechaLimite;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "COSTE_ENTRADA")
    private Double costeEntrada;
    @Column(name = "AFORO")
    private Integer aforo;
    @Column(name = "MAX_ENTRADAS")
    private Integer maxEntradas;
    @Column(name = "ASIENTOS_FIJOS")
    private Character asientosFijos;
    @Column(name = "NUM_FILAS")
    private Integer numFilas;
    @Column(name = "NUM_ASIENTOS_FILA")
    private Integer numAsientosFila;
    @JoinTable(name = "EVENTOETIQUETA", joinColumns = {
        @JoinColumn(name = "EVENTO_ID", referencedColumnName = "ID")}, inverseJoinColumns = {
        @JoinColumn(name = "ETIQUETA_ID", referencedColumnName = "ID")})
    @ManyToMany
    private List<Etiqueta> etiquetaList;
    @JoinColumn(name = "CREADOREVENTOS_ID", referencedColumnName = "USUARIO_ID")
    @ManyToOne(optional = false)
    private Creadoreventos creadoreventosId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "evento")
    private List<Reserva> reservaList;
    
    
    public Evento() {
    }

    public Evento(Integer id) {
        this.id = id;
    }

    public Evento(Integer id, String titulo, Date fecha) {
        this.id = id;
        this.titulo = titulo;
        this.fecha = fecha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(Date fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public Double getCosteEntrada() {
        return costeEntrada;
    }

    public void setCosteEntrada(Double costeEntrada) {
        this.costeEntrada = costeEntrada;
    }

    public Integer getAforo() {
        return aforo;
    }

    public void setAforo(Integer aforo) {
        this.aforo = aforo;
    }

    public Integer getMaxEntradas() {
        return maxEntradas;
    }

    public void setMaxEntradas(Integer maxEntradas) {
        this.maxEntradas = maxEntradas;
    }

    public Character getAsientosFijos() {
        return asientosFijos;
    }

    public void setAsientosFijos(Character asientosFijos) {
        this.asientosFijos = asientosFijos;
    }

    public Integer getNumFilas() {
        return numFilas;
    }

    public void setNumFilas(Integer numFilas) {
        this.numFilas = numFilas;
    }

    public Integer getNumAsientosFila() {
        return numAsientosFila;
    }

    public void setNumAsientosFila(Integer numAsientosFila) {
        this.numAsientosFila = numAsientosFila;
    }

    @XmlTransient
    public List<Etiqueta> getEtiquetaList() {
        return etiquetaList;
    }

    public void setEtiquetaList(List<Etiqueta> etiquetaList) {
        this.etiquetaList = etiquetaList;
    }

    public Creadoreventos getCreadoreventosId() {
        return creadoreventosId;
    }

    public void setCreadoreventosId(Creadoreventos creadoreventosId) {
        this.creadoreventosId = creadoreventosId;
    }

    @XmlTransient
    public List<Reserva> getReservaList() {
        return reservaList;
    }

    public void setReservaList(List<Reserva> reservaList) {
        this.reservaList = reservaList;
    }
    
    public void inicializar(int[][] asientos){
        for(int i = 0; i < asientos.length; i++){
            for(int o = 0; o < asientos[0].length; o++){
                asientos[i][o]=0;
            }
        }
    }
    
    public int[][] matrizAsientos(){
        int[][] asientos = new int[this.numFilas][this.numAsientosFila];
        inicializar(asientos);
        this.reservaList.forEach((r) -> {
            asientos[r.getReservaPK().getFila()-1][r.getReservaPK().getAsiento()-1] = 1;
        });
        return asientos;
    }
    
    public int getEntradasReservadas(Usuario u){
        int reservas = 0;
        if(!this.reservaList.isEmpty()){
            for(Reserva r: this.reservaList){
                if(r.getUsuarioeventosId().getUsuarioId() == u.getId()){
                    reservas++;
                }
            }
        }
        return reservas;
    }   
    
    public int asientosDisponibles(){
        if(this.aforo == null && this.asientosFijos == 'n'){
            return 10000;
        }
        int reservas = 0;
        if(!this.reservaList.isEmpty()){
            reservas = this.reservaList.size();
        }
        if(this.aforo != null && this.asientosFijos == 'n'){
            return this.aforo - reservas;
        }else{
            return this.numAsientosFila * this.numFilas - reservas;
        }
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
        if (!(object instanceof Evento)) {
            return false;
        }
        Evento other = (Evento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Evento[ id=" + id + " ]";
    }
    
}
