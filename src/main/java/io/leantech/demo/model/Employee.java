package io.leantech.demo.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table
public class Employee {
	
	@Id
	@SequenceGenerator(
			name = "employee_sequence", 
			sequenceName = "employee_sequence", 
			allocationSize = 1
	)
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE, 
			generator = "employee_sequence"
	)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="PERSON_ID")
	private Person person;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="POSITION_ID")
	private Position position;
	
	private Double salary;

	public Employee() {
		
	}

	public Employee(Long id, Person person, Position position, Double salary) {
		this.id = id;
		this.person = person;
		this.position = position;
		this.salary = salary;
	}

	public Employee(Person person, Position position, Double salary) {
		this.person = person;
		this.position = position;
		this.salary = salary;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}
	
}
