package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleSumaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(value = "SELECT sa FROM Sale sa JOIN FETCH sa.seller " +
            "WHERE UPPER(sa.seller.name) LIKE CONCAT('%', UPPER(:name),'%') " +
            "AND sa.date BETWEEN :minDate AND :maxDate",
            countQuery = "SELECT COUNT(sa) FROM Sale sa " +
                    "WHERE UPPER(sa.seller.name) LIKE CONCAT('%', UPPER(:name),'%') " +
                    "AND sa.date BETWEEN :minDate AND :maxDate")
    Page<Sale> searchSaleByNameBetweenDates(String name, LocalDate minDate, LocalDate maxDate, Pageable pageable);

    @Query("SELECT new com.devsuperior.dsmeta.dto.SaleSumaryDTO(sa.seller.name, SUM(sa.amount) AS total) " +
            "FROM Sale sa " +
            "WHERE date BETWEEN :minDate AND :maxDate " +
            "GROUP BY  sa.seller.id " +
            "ORDER BY  sa.seller.name")
    List<SaleSumaryDTO> searchSumaryBetweenDates(LocalDate minDate, LocalDate maxDate);
}
