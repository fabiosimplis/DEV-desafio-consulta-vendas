package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT new com.devsuperior.dsmeta.dto.SaleMinDTO(sa.id, sa.amount, sa.date, sa.seller.name) " +
            "FROM Sale sa " +
            "WHERE UPPER(sa.seller.name) LIKE UPPER(CONCAT('%',:name,'%')) " +
            "AND sa.date BETWEEN :minDate AND :maxDate")
    List<SaleMinDTO> searchSaleByNameBetweenDates(String name, LocalDate minDate, LocalDate maxDate);
}
