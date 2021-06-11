package io.leantech.demo.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

public class PositionDTO {
	
	private Long id;
	private String name;
	@JsonManagedReference
	private List<EmployeeDTO> employees;

	public PositionDTO() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<EmployeeDTO> getEmployees() {
		return employees;
	}

	public void setEmployees(List<EmployeeDTO> employees) {
		this.employees = employees;
	}
	
}
