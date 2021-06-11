package io.leantech.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.leantech.demo.dto.EmployeeDTO;
import io.leantech.demo.dto.FilterDTO;
import io.leantech.demo.exception.ApiRequestException;
import io.leantech.demo.exception.NotFoundRequestException;
import io.leantech.demo.model.Employee;
import io.leantech.demo.model.Person;
import io.leantech.demo.model.Position;
import io.leantech.demo.repository.EmployeeRepository;
import io.leantech.demo.repository.PersonRepository;
import io.leantech.demo.repository.PositionRepository;

@Service
public class EmployeeService {
	
	private EmployeeRepository employeeRepository;
	
	private PersonRepository personRepository;
	
	private PositionRepository positionRepository;
	
	private ModelMapper modelMapper;

	@Autowired
	public EmployeeService(
		EmployeeRepository employeeRepository,
		PersonRepository personRepository,
		PositionRepository positionRepository, 
		ModelMapper modelMapper
	) {
		this.employeeRepository = employeeRepository;
		this.personRepository = personRepository;
		this.positionRepository = positionRepository;
		this.modelMapper = modelMapper;
	}
	
	public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
		Optional<Position> position = positionRepository.findByName(employeeDTO.getPosition().getName().toLowerCase());
		Person person = modelMapper.map(employeeDTO.getPerson(), Person.class);
		personRepository.save(person);
		Employee employee = new Employee();
		employee.setPerson(person);
		employee.setPosition(position.get());
		employee.setSalary(employeeDTO.getSalary());
		employeeRepository.save(employee);
		return modelMapper.map(employee, EmployeeDTO.class);
	}
	
	
	public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO) {
		Optional<Employee> employee = employeeRepository.findById(employeeDTO.getId());
		if (!employee.isPresent()) {
			throw new NotFoundRequestException("The employee doesn't exist in the database");
		}
		Optional<Position> position = positionRepository.findById(employeeDTO.getPosition().getId());
		if (!position.isPresent()) {
			throw new NotFoundRequestException("The position doesn't exist in the database");
		}
		
		employee.get().getPerson().setName(employeeDTO.getPerson().getName());
		employee.get().getPerson().setLastName(employeeDTO.getPerson().getLastName());
		employee.get().getPerson().setAddress(employeeDTO.getPerson().getAddress());
		employee.get().getPerson().setCellphone(employeeDTO.getPerson().getCellphone());
		employee.get().getPerson().setCityName(employeeDTO.getPerson().getCityName());
		personRepository.save(employee.get().getPerson());
		
		employee.get().setPosition(position.get());
		employee.get().setSalary(employeeDTO.getSalary());
		employeeRepository.save(employee.get());
		
		return modelMapper.map(employee.get(), EmployeeDTO.class);
	}
	
	public void deleteEmployee(Long employeeId) {
		Optional<Employee> employee = employeeRepository.findById(employeeId);
		if (!employee.isPresent()) {
			throw new NotFoundRequestException("The employee doesn't exist in the database");
		}
		Optional<Person> person = personRepository.findById(employee.get().getPerson().getId());
		if (!person.isPresent()) {
			throw new NotFoundRequestException("The person doesn't exist in the database");
		}
		
		employeeRepository.delete(employee.get());
		personRepository.delete(person.get());
	}
	
	public List<EmployeeDTO> getEmployees(FilterDTO filterDTO) {
		List<Employee> list = null;
		Optional<String> name = Optional.of(filterDTO.getName());
		Optional<String> position = Optional.of(filterDTO.getPosition());
		
		if (!name.get().isEmpty() && !position.get().isEmpty()) {
			throw new ApiRequestException("Both parameter are not allowed");
		}
		
		if (!name.get().isEmpty() && position.get().isEmpty()) {
			list = employeeRepository.findByName(name.get());
		}
		
		if (!position.get().isEmpty() && name.get().isEmpty()) {
			list = employeeRepository.findByPosition(position.get());
		}
		
		if (position.get().isEmpty() && name.get().isEmpty()) {		
			list = employeeRepository.findAll();
		}
		
		List<EmployeeDTO> dtoList = list
				.stream()
				.map(employee -> modelMapper.map(employee, EmployeeDTO.class))
				.collect(Collectors.toList());
		return dtoList;
	}

}
