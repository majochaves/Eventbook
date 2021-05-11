package entity;

import entity.Administrador;
import entity.Analista;
import entity.Chat;
import entity.Creadoreventos;
import entity.Teleoperador;
import entity.Usuarioeventos;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-05-11T19:18:06")
@StaticMetamodel(Usuario.class)
public class Usuario_ { 

    public static volatile SingularAttribute<Usuario, String> apellidos;
    public static volatile SingularAttribute<Usuario, Teleoperador> teleoperador;
    public static volatile SingularAttribute<Usuario, Administrador> administrador;
    public static volatile SingularAttribute<Usuario, Analista> analista;
    public static volatile SingularAttribute<Usuario, String> nombre;
    public static volatile SingularAttribute<Usuario, Creadoreventos> creadoreventos;
    public static volatile SingularAttribute<Usuario, String> password;
    public static volatile SingularAttribute<Usuario, String> domicilio;
    public static volatile SingularAttribute<Usuario, String> ciudadResidencia;
    public static volatile SingularAttribute<Usuario, Date> fechaCreacion;
    public static volatile SingularAttribute<Usuario, Integer> id;
    public static volatile SingularAttribute<Usuario, String> sexo;
    public static volatile SingularAttribute<Usuario, Usuarioeventos> usuarioeventos;
    public static volatile SingularAttribute<Usuario, String> username;
    public static volatile ListAttribute<Usuario, Chat> chatList;

}