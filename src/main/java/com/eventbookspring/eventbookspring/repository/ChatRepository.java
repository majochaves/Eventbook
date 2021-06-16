package com.eventbookspring.eventbookspring.repository;

import com.eventbookspring.eventbookspring.entity.Chat;
import com.eventbookspring.eventbookspring.entity.ChatPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, ChatPK> {
}