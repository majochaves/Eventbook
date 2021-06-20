package com.eventbookspring.eventbookspring.service;

import com.eventbookspring.eventbookspring.entity.Creadoreventos;
import com.eventbookspring.eventbookspring.repository.CreadoreventosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreadorEventosService {
    private CreadoreventosRepository creadoreventosRepository;

    @Autowired
    public void setCreadoreventosRepository(CreadoreventosRepository creadoreventosRepository) {
        this.creadoreventosRepository = creadoreventosRepository;
    }

    public Creadoreventos getUserById(Integer id){
        return this.creadoreventosRepository.getById(id);
    }
}
