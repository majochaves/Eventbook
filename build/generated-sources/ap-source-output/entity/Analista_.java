package entity;

import entity.Analisis;
import entity.Usuario;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-05-15T20:07:37")
@StaticMetamodel(Analista.class)
public class Analista_ { 

    public static volatile SingularAttribute<Analista, Usuario> usuario;
    public static volatile SingularAttribute<Analista, Integer> usuarioId;
    public static volatile ListAttribute<Analista, Analisis> analisisList;

}