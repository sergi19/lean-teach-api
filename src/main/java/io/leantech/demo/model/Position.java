package io.leantech.demo.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table
public class Position {

	@Id
	@SequenceGenerator(
			name = "position_sequence", 
			sequenceName = "position_sequence", 
			allocationSize = 1
	)
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE, 
			generator = "position_sequence"
	)
	private Long id;
	
	private String name;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "position")
	private List<Employee> employees;
	
	public Position() {
		
	}

	public Position(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Position(String name) {
		this.name = name;
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

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
	
}
