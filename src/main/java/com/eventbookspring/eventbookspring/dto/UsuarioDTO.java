package com.eventbookspring.eventbookspring.dto;

import java.util.Date;

public class UsuarioDTO {

    private Integer id;
    private String nombre;
    private String apellidos;
    private String domicilio;
    private String ciudadResidencia;
    private String password;
    private String username;
    private String sexo;
    private Date fechaCreacion;

    private Integer administrador;
    private Integer analista;
    private Integer usuarioeventos;
    private Integer teleoperador;
    private Integer creadoreventos;

    public UsuarioDTO() {

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

    public Integer getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Integer administrador) {
        this.administrador = administrador;
    }

    public Integer getAnalista() {
        return analista;
    }

    public void setAnalista(Integer analista) {
        this.analista = analista;
    }

    public Integer getUsuarioeventos() {
        return usuarioeventos;
    }

    public void setUsuarioeventos(Integer usuarioeventos) {
        this.usuarioeventos = usuarioeventos;
    }

    public Integer getTeleoperador() {
        return teleoperador;
    }

    public void setTeleoperador(Integer teleoperador) {
        this.teleoperador = teleoperador;
    }

    public Integer getCreadoreventos() {
        return creadoreventos;
    }

    public void setCreadoreventos(Integer creadoreventos) {
        this.creadoreventos = creadoreventos;
    }


}
