package entity;

import entity.Analista;
import entity.Tipoanalisis;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-04-28T04:06:23")
@StaticMetamodel(Analisis.class)
public class Analisis_ { 

    public static volatile SingularAttribute<Analisis, String> descripcion;
    public static volatile SingularAttribute<Analisis, Integer> id;
    public static volatile ListAttribute<Analisis, Tipoanalisis> tipoanalisisList;
    public static volatile SingularAttribute<Analisis, Analista> analistaUsuarioId;

}