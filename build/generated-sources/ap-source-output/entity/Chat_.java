package entity;

import entity.ChatPK;
import entity.Mensaje;
import entity.Teleoperador;
import entity.Usuario;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-05-10T20:36:19")
@StaticMetamodel(Chat.class)
public class Chat_ { 

    public static volatile SingularAttribute<Chat, Teleoperador> teleoperador;
    public static volatile SingularAttribute<Chat, Date> fecha;
    public static volatile SingularAttribute<Chat, ChatPK> chatPK;
    public static volatile ListAttribute<Chat, Mensaje> mensajeList;
    public static volatile SingularAttribute<Chat, Usuario> usuario;

}