package com.eventbookspring.eventbookspring.service;

import com.eventbookspring.eventbookspring.dto.TeleoperadorDTO;
import com.eventbookspring.eventbookspring.entity.Teleoperador;
import com.eventbookspring.eventbookspring.repository.TeleoperadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeleoperadorService {
    private TeleoperadorRepository teleoperadorRepository;

    @Autowired
    public void setTeleoperadorRepository(TeleoperadorRepository teleoperadorRepository) {
        this.teleoperadorRepository = teleoperadorRepository;
    }

    public List<TeleoperadorDTO> findAll() {
        List<Teleoperador> teleoperadorList =  this.teleoperadorRepository.findAll();
        List<TeleoperadorDTO> teleoperadorDTOList = new ArrayList<>();
        for (Teleoperador teleop : teleoperadorList){
            teleoperadorDTOList.add(teleop.getDTO());
        }
       return teleoperadorDTOList;

    }

    public TeleoperadorDTO findByUsuarioId(Integer id) {
        return this.teleoperadorRepository.findByUsuarioId(id).getDTO();
    }
}
