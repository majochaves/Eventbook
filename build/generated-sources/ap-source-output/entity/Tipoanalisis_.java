package entity;

import entity.Analisis;
import entity.Campoanalisis;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-04-28T21:48:03")
@StaticMetamodel(Tipoanalisis.class)
public class Tipoanalisis_ { 

    public static volatile SingularAttribute<Tipoanalisis, Analisis> analisisId;
    public static volatile ListAttribute<Tipoanalisis, Campoanalisis> campoanalisisList;
    public static volatile SingularAttribute<Tipoanalisis, Integer> id;
    public static volatile SingularAttribute<Tipoanalisis, String> nombre;

}