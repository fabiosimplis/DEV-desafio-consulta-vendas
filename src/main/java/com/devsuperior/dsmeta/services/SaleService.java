package com.devsuperior.dsmeta.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SaleSumaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public List<SaleMinDTO> searchSaleByNameBetweenDates(String name, String minDate, String maxDate){

		if (minDate.isEmpty()){
			minDate = oneYearsPastFromToday();
		}

		if (maxDate.isEmpty()){
			maxDate = String.valueOf(LocalDate.now());
		}

		return repository.searchSaleByNameBetweenDates(name, LocalDate.parse(minDate), LocalDate.parse(maxDate));
	}

	public List<SaleSumaryDTO> searchSumaryBetweenDates(String minDate, String maxDate){

		if (minDate.isEmpty()){
			minDate = oneYearsPastFromToday();
		}

		if (maxDate.isEmpty()){
			maxDate = String.valueOf(LocalDate.now());
		}

		return repository.searchSumaryBetweenDates(LocalDate.parse(minDate), LocalDate.parse(maxDate));
	}



	private String oneYearsPastFromToday(){
		LocalDate today = LocalDate.now();
		LocalDate oneYearPast = today.minusYears(1L);
		return String.valueOf(oneYearPast);
	}
}
