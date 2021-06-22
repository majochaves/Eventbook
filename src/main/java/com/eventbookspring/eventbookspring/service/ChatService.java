package com.eventbookspring.eventbookspring.service;

import com.eventbookspring.eventbookspring.entity.Chat;
import com.eventbookspring.eventbookspring.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {
    private ChatRepository chatRepository;

    @Autowired
    public void setChatRepository(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }


    public List<Chat> findChatsByUserID(Integer id) {
        return this.chatRepository.findChatsByUserID(id);
    }

    public List<Chat> findAll() {
        return this.chatRepository.findAll();
    }
}
