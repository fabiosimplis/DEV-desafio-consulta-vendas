package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleSumaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT new com.devsuperior.dsmeta.dto.SaleMinDTO(sa.id, sa.amount, sa.date, sa.seller.name) " +
            "FROM Sale sa " +
            "WHERE UPPER(sa.seller.name) LIKE UPPER(CONCAT('%',:name,'%')) " +
            "AND sa.date BETWEEN :minDate AND :maxDate " +
            "AND sa IN :sales")
    List<SaleMinDTO> searchSaleByNameBetweenDates(String name, LocalDate minDate, LocalDate maxDate, List<Sale> sales);

    @Query("SELECT new com.devsuperior.dsmeta.dto.SaleSumaryDTO(sa.seller.name, SUM(sa.amount) AS total) " +
            "FROM Sale sa " +
            "WHERE date BETWEEN :minDate AND :maxDate " +
            "GROUP BY  sa.seller.id " +
            "ORDER BY  sa.seller.name")
    List<SaleSumaryDTO> searchSumaryBetweenDates(LocalDate minDate, LocalDate maxDate);
}
