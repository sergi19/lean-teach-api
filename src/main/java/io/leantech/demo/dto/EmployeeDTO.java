package io.leantech.demo.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;

public class EmployeeDTO {

	private Long id;
	private Double salary;
	private PersonDTO person;
	@JsonBackReference
	private PositionDTO position;
	
	public EmployeeDTO() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PersonDTO getPerson() {
		return person;
	}

	public void setPerson(PersonDTO person) {
		this.person = person;
	}

	public PositionDTO getPosition() {
		return position;
	}

	public void setPosition(PositionDTO position) {
		this.position = position;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}
	
}
