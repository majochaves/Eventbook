package entity;

import entity.Creadoreventos;
import entity.Etiqueta;
import entity.Reserva;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-05-11T19:18:06")
@StaticMetamodel(Evento.class)
public class Evento_ { 

    public static volatile SingularAttribute<Evento, String> descripcion;
    public static volatile SingularAttribute<Evento, Integer> aforo;
    public static volatile ListAttribute<Evento, Etiqueta> etiquetaList;
    public static volatile SingularAttribute<Evento, String> titulo;
    public static volatile SingularAttribute<Evento, Integer> maxEntradas;
    public static volatile SingularAttribute<Evento, Integer> numAsientosFila;
    public static volatile SingularAttribute<Evento, Creadoreventos> creadoreventosId;
    public static volatile SingularAttribute<Evento, Date> fecha;
    public static volatile SingularAttribute<Evento, Double> costeEntrada;
    public static volatile SingularAttribute<Evento, Integer> numFilas;
    public static volatile SingularAttribute<Evento, Character> asientosFijos;
    public static volatile ListAttribute<Evento, Reserva> reservaList;
    public static volatile SingularAttribute<Evento, Date> fechaLimite;
    public static volatile SingularAttribute<Evento, Integer> id;

}