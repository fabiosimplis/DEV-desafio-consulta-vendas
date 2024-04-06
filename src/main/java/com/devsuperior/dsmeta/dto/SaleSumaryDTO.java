package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.entities.Sale;

import java.time.LocalDate;

public class SaleSumaryDTO {

	private String name;
	private Double total;


	public SaleSumaryDTO(Sale entity) {
		total = entity.getAmount();
		name = entity.getSeller().getName();
	}
	public SaleSumaryDTO(String name, Double total) {
		this.name = name;
		this.total = total;
	}

	public Double getTotal() {
		return total;
	}

	public String getName() {
		return name;
	}
}
