package com.eventbookspring.eventbookspring.repository;

import com.eventbookspring.eventbookspring.entity.Chat;
import com.eventbookspring.eventbookspring.entity.ChatPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, ChatPK> {
    @Query("SELECT c FROM Chat c WHERE  c.chatPK.teleoperadorId = :userID OR c.chatPK.usuarioId = :userID")
    public List<Chat> findChatsByUserID(Integer userID);

    @Query("SELECT c FROM Chat c WHERE  (c.chatPK.teleoperadorId = :teleoperadorId AND c.chatPK.usuarioId = :usuarioId) OR (c.chatPK.teleoperadorId = :usuarioId AND c.chatPK.usuarioId = :teleoperadorId)")
    Chat getByChatPK(Integer usuarioId, Integer teleoperadorId);
}