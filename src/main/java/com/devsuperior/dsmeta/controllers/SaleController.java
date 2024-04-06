package com.devsuperior.dsmeta.controllers;

import com.devsuperior.dsmeta.dto.SaleSumaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.services.SaleService;

import java.util.List;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
		SaleMinDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}

	@GetMapping(value = "/report")
	public ResponseEntity<List<SaleMinDTO>> getReport(@RequestParam(value = "name", defaultValue = "") String name,
									   @RequestParam(value = "minDate", defaultValue = "") String minDate,
									   @RequestParam(value = "maxDate", defaultValue = "") String maxDate) {

		List<SaleMinDTO> reportList = service.searchSaleByNameBetweenDates(name, minDate, maxDate);

		return ResponseEntity.ok(reportList);
	}

	@GetMapping(value = "/summary")
	public ResponseEntity<List<SaleSumaryDTO>> getSummary(@RequestParam(value = "minDate", defaultValue = "") String minDate,
														  @RequestParam(value = "maxDate", defaultValue = "") String maxDate) {

		List<SaleSumaryDTO> reportList = service.searchSumaryBetweenDates(minDate, maxDate);

		return ResponseEntity.ok(reportList);

	}
}
