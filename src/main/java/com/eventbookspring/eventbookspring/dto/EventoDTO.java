package com.eventbookspring.eventbookspring.dto;

import com.eventbookspring.eventbookspring.entity.Creadoreventos;
import com.eventbookspring.eventbookspring.entity.Etiqueta;
import com.eventbookspring.eventbookspring.entity.Reserva;
import com.eventbookspring.eventbookspring.entity.Usuario;

import java.util.List;

public class EventoDTO {
    private Integer id;
    private String titulo;
    private String descripcion;
    private String fecha;
    private String fechaLimite;
    private Double costeEntrada;
    private Integer aforo;
    private Integer maxEntradas;
    private Character asientosFijos;
    private Integer numFilas;
    private Integer numAsientosFila;
    private List<Etiqueta> etiquetaList;
    private List<Reserva> reservaList;
    private Creadoreventos creadoreventos;

    public void inicializar(int[][] asientos){
        for(int i = 0; i < asientos.length; i++){
            for(int o = 0; o < asientos[0].length; o++){
                asientos[i][o]=0;
            }
        }
    }

    public int[][] getMatrizAsientos(){
        int[][] asientos = new int[this.numFilas][this.numAsientosFila];
        inicializar(asientos);
        this.reservaList.forEach((r) -> {
            asientos[r.getReservaPK().getFila()-1][r.getReservaPK().getAsiento()-1] = r.getUsuarioeventosId().getUsuarioId();
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

    public int getAsientosDisponibles(){
        if(this.aforo == null && this.asientosFijos == 'N'){
            return 10000;
        }
        int reservas = 0;
        if(!this.reservaList.isEmpty()){
            reservas = this.reservaList.size();
        }
        if(this.aforo != null && this.asientosFijos == 'N'){
            return this.aforo - reservas;
        }else{
            return this.numAsientosFila * this.numFilas - reservas;
        }
    }


    public List<Reserva> getReservaList() {
        return reservaList;
    }

    public void setReservaList(List<Reserva> reservaList) {
        this.reservaList = reservaList;
    }

    public Creadoreventos getCreadoreventos() {
        return creadoreventos;
    }

    public void setCreadoreventos(Creadoreventos creadoreventos) {
        this.creadoreventos = creadoreventos;
    }

    public EventoDTO(){}

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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(String fechaLimite) {
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

    public List<Etiqueta> getEtiquetaList() {
        return etiquetaList;
    }

    public void setEtiquetaList(List<Etiqueta> etiquetaList) {
        this.etiquetaList = etiquetaList;
    }
}
