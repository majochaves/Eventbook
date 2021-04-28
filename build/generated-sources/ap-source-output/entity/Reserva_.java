package entity;

import entity.Evento;
import entity.ReservaPK;
import entity.Usuarioeventos;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-04-28T15:52:40")
@StaticMetamodel(Reserva.class)
public class Reserva_ { 

    public static volatile SingularAttribute<Reserva, ReservaPK> reservaPK;
    public static volatile SingularAttribute<Reserva, Date> fecha;
    public static volatile SingularAttribute<Reserva, Usuarioeventos> usuarioeventosId;
    public static volatile SingularAttribute<Reserva, Evento> evento;

}