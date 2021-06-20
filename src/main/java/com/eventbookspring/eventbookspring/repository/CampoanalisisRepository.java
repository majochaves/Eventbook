package com.eventbookspring.eventbookspring.repository;

import com.eventbookspring.eventbookspring.entity.Campoanalisis;
import com.eventbookspring.eventbookspring.entity.CampoanalisisPK;
import com.eventbookspring.eventbookspring.entity.Tipoanalisis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CampoanalisisRepository extends JpaRepository<Campoanalisis, CampoanalisisPK> {

    @Modifying
    @Query("DELETE FROM Campoanalisis c WHERE c.tipoanalisis = :tipoanalisis")
    public void deleteCampoanalisisByTipoanalisis(Tipoanalisis tipoanalisis);
}