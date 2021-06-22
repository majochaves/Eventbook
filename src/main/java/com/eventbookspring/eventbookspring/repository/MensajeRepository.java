package com.eventbookspring.eventbookspring.repository;

import com.eventbookspring.eventbookspring.entity.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;

public interface MensajeRepository extends JpaRepository<Mensaje, Integer> {

    @Query("SELECT m FROM Mensaje m WHERE "
            + "(m.chat.chatPK.teleoperadorId = :usuarioEmisorIdA AND m.chat.chatPK.usuarioId = :usuarioEmisorIdB) OR"
            + "(m.chat.chatPK.teleoperadorId = :usuarioEmisorIdB AND m.chat.chatPK.usuarioId = :usuarioEmisorIdA)"
            + "ORDER BY m.fecha")
    List<Mensaje> getListOfMensajesByIDs(Integer usuarioEmisorIdA, Integer usuarioEmisorIdB);
}