package com.devsuperior.dsmeta.services;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleSumaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	//public List<SaleMinDTO> searchSaleByNameBetweenDates(String name, String minDate, String maxDate){
	public Page<SaleMinDTO> searchSaleByNameBetweenDates(String name, String minDate, String maxDate, PageRequest pageRequest){

		if (minDate.isEmpty()){
			minDate = oneYearsPastFromToday();
		}

		if (maxDate.isEmpty()){
			maxDate = String.valueOf(LocalDate.now());
		}

		Page<Sale> page = repository.findAll(pageRequest);
		List<Sale> listOfSales = page.stream().collect(Collectors.toList());
		repository.searchSaleByNameBetweenDates(name, LocalDate.parse(minDate), LocalDate.parse(maxDate), listOfSales);

		return page.map(SaleMinDTO::new);
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
