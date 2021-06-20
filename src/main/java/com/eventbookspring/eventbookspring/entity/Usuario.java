/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventbookspring.eventbookspring.entity;

import com.eventbookspring.eventbookspring.dto.AnalisisDTO;
import com.eventbookspring.eventbookspring.dto.TipoanalisisDTO;
import com.eventbookspring.eventbookspring.dto.UsuarioDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.*;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author memoriasIT
 */
@Entity
@Table(name = "USUARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")
    , @NamedQuery(name = "Usuario.findById", query = "SELECT u FROM Usuario u WHERE u.id = :id")
    , @NamedQuery(name = "Usuario.findByNombre", query = "SELECT u FROM Usuario u WHERE u.nombre = :nombre")
    , @NamedQuery(name = "Usuario.findByApellidos", query = "SELECT u FROM Usuario u WHERE u.apellidos = :apellidos")
    , @NamedQuery(name = "Usuario.findByDomicilio", query = "SELECT u FROM Usuario u WHERE u.domicilio = :domicilio")
    , @NamedQuery(name = "Usuario.findByCiudadResidencia", query = "SELECT u FROM Usuario u WHERE u.ciudadResidencia = :ciudadResidencia")
    , @NamedQuery(name = "Usuario.findByPassword", query = "SELECT u FROM Usuario u WHERE u.password = :password")
    , @NamedQuery(name = "Usuario.findByUsername", query = "SELECT u FROM Usuario u WHERE u.username = :username")
    , @NamedQuery(name = "Usuario.findBySexo", query = "SELECT u FROM Usuario u WHERE u.sexo = :sexo")
    , @NamedQuery(name = "Usuario.findByFechaCreacion", query = "SELECT u FROM Usuario u WHERE u.fechaCreacion = :fechaCreacion")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "NOMBRE", length = 50)
    private String nombre;
    @Column(name = "APELLIDOS", length = 50)
    private String apellidos;
    @Column(name = "DOMICILIO", length = 50)
    private String domicilio;
    @Column(name = "CIUDAD_RESIDENCIA", length = 50)
    private String ciudadResidencia;
    @Basic(optional = false)
    @Column(name = "PASSWORD", length = 50, nullable = false)
    private String password;
    @Basic(optional = false)
    @Column(name = "USERNAME", length = 50, nullable = false)
    private String username;
    @Column(name = "SEXO", length = 20)
    private String sexo;
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Administrador administrador;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Analista analista;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Usuarioeventos usuarioeventos;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Teleoperador teleoperador;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private List<Chat> chatList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Creadoreventos creadoreventos;

    public Usuario() {
        chatList = new LinkedList<>();
    }

    public Usuario(Integer id) {
        this();
        this.id = id;
    }

    public Usuario(Integer id, String password, String username) {
        this();
        this.id = id;
        this.password = password;
        this.username = username;
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

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getCiudadResidencia() {
        return ciudadResidencia;
    }

    public void setCiudadResidencia(String ciudadResidencia) {
        this.ciudadResidencia = ciudadResidencia;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Administrador getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }

    public Analista getAnalista() {
        return analista;
    }

    public void setAnalista(Analista analista) {
        this.analista = analista;
    }

    public Usuarioeventos getUsuarioeventos() {
        return usuarioeventos;
    }

    public void setUsuarioeventos(Usuarioeventos usuarioeventos) {
        this.usuarioeventos = usuarioeventos;
    }

    public Teleoperador getTeleoperador() {
        return teleoperador;
    }

    public void setTeleoperador(Teleoperador teleoperador) {
        this.teleoperador = teleoperador;
    }

    @XmlTransient
    public List<Chat> getChatList() {
        return chatList;
    }

    public void setChatList(List<Chat> chatList) {
        this.chatList = chatList;
    }

    public Creadoreventos getCreadoreventos() {
        return creadoreventos;
    }

    public void setCreadoreventos(Creadoreventos creadoreventos) {
        this.creadoreventos = creadoreventos;
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
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Usuario[ id=" + id + " ]";
    }

    @Transient
    public UsuarioDTO getDTO(){
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(id);
        usuarioDTO.setNombre(nombre);
        usuarioDTO.setApellidos(apellidos);
        usuarioDTO.setUsername(username);
        usuarioDTO.setPassword(password);
        usuarioDTO.setSexo(sexo);
        usuarioDTO.setDomicilio(domicilio);
        usuarioDTO.setCiudadResidencia(ciudadResidencia);

        usuarioDTO.setFechaCreacion(fechaCreacion);

        usuarioDTO.setUsuarioeventos(usuarioeventos == null ? null : usuarioeventos.getUsuarioId());
        usuarioDTO.setAdministrador(administrador == null ? null : administrador.getUsuarioId());
        usuarioDTO.setTeleoperador(teleoperador == null ? null : teleoperador.getUsuarioId());
        usuarioDTO.setAnalista(analista == null ? null : analista.getUsuarioId());
        usuarioDTO.setCreadoreventos(creadoreventos == null ? null : creadoreventos.getUsuarioId());

        return usuarioDTO;
    }
}
