package entity;

import entity.Chat;
import entity.Usuario;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-05-15T20:07:37")
@StaticMetamodel(Teleoperador.class)
public class Teleoperador_ { 

    public static volatile SingularAttribute<Teleoperador, Usuario> usuario;
    public static volatile SingularAttribute<Teleoperador, Integer> usuarioId;
    public static volatile ListAttribute<Teleoperador, Chat> chatList;

}