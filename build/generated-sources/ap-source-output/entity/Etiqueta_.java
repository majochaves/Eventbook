package entity;

import entity.Evento;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-05-14T23:33:37")
@StaticMetamodel(Etiqueta.class)
public class Etiqueta_ { 

    public static volatile SingularAttribute<Etiqueta, String> descripcion;
    public static volatile ListAttribute<Etiqueta, Evento> eventoList;
    public static volatile SingularAttribute<Etiqueta, Integer> id;

}