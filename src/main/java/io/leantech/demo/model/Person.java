package io.leantech.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table
public class Person {

	@Id
	@SequenceGenerator(
			name = "person_sequence", 
			sequenceName = "person_sequence", 
			allocationSize = 1
	)
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE, 
			generator = "person_sequence"
	)
	private Long id;
	
	private String name;
	
	private String lastName;
	
	private String address;
	
	private String cellphone;
	
	private String cityName;
	
	public Person() {
		
	}
	
	public Person(Long id, String name, String lastName, String address, String cellphone, String cityName) {
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.address = address;
		this.cellphone = cellphone;
		this.cityName = cityName;
	}
	
	public Person(String name, String lastName, String address, String cellphone, String cityName) {
		this.name = name;
		this.lastName = lastName;
		this.address = address;
		this.cellphone = cellphone;
		this.cityName = cityName;
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
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getCellphone() {
		return cellphone;
	}
	
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
	
	public String getCityName() {
		return cityName;
	}
	
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	
}
