package entity;

import entity.Chat;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-04-26T11:21:37")
@StaticMetamodel(Mensaje.class)
public class Mensaje_ { 

    public static volatile SingularAttribute<Mensaje, Date> fecha;
    public static volatile SingularAttribute<Mensaje, String> contenido;
    public static volatile SingularAttribute<Mensaje, Integer> usuarioEmisorId;
    public static volatile SingularAttribute<Mensaje, Chat> chat;
    public static volatile SingularAttribute<Mensaje, Integer> id;

}