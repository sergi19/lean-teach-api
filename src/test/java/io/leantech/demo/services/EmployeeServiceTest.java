package io.leantech.demo.services;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import io.leantech.demo.dto.EmployeeDTO;
import io.leantech.demo.dto.FilterDTO;
import io.leantech.demo.dto.PersonDTO;
import io.leantech.demo.dto.PositionDTO;
import io.leantech.demo.exception.ApiRequestException;
import io.leantech.demo.exception.NotFoundRequestException;
import io.leantech.demo.model.Employee;
import io.leantech.demo.model.Person;
import io.leantech.demo.model.Position;
import io.leantech.demo.repository.EmployeeRepository;
import io.leantech.demo.repository.PersonRepository;
import io.leantech.demo.repository.PositionRepository;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {
	
	@Mock
	private ModelMapper modelMapper;
	
	@Mock
	private EmployeeService employeeServiceSpy;
	
	@InjectMocks
	private EmployeeService employeeService;
	
	private EmployeeRepository employeeRepositoryMock;
	private PositionRepository positionRepositoryMock;
	private PersonRepository personRepositoryMock;
	
	private EmployeeDTO employeeDTO;
	private PersonDTO personDTO;
	private PositionDTO positionDTO;
	
	@Before
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		
		positionDTO = new PositionDTO();
		positionDTO.setId(1L);
		positionDTO.setName("dev");
		
		personDTO = new PersonDTO();
		personDTO.setId(1L);
		personDTO.setName("Person");
		
		employeeDTO = new EmployeeDTO();
		employeeDTO.setId(1L);
		employeeDTO.setPerson(personDTO);
		employeeDTO.setPosition(positionDTO);
		
		employeeRepositoryMock = Mockito.mock(EmployeeRepository.class);
		personRepositoryMock = Mockito.mock(PersonRepository.class);
		positionRepositoryMock = Mockito.mock(PositionRepository.class);
		
		employeeService = new EmployeeService(employeeRepositoryMock, personRepositoryMock, positionRepositoryMock, modelMapper);
		employeeServiceSpy = Mockito.spy(employeeService);
	}
	
	@Test
	public void createEmployee() {
		Position position = new Position();
		position.setId(1L);
		position.setName("dev");
		Optional<Position> optional = Optional.of(position);
		Mockito.when(positionRepositoryMock.findByName(employeeDTO.getPosition().getName())).thenReturn(optional);
		
		Person person = new Person();
		person.setId(1L);
		Mockito.when(personRepositoryMock.save(person)).thenReturn(person);
		
		Employee employee = new Employee();
		employee.setId(1L);
		employee.setPerson(person);
		employee.setPosition(position);
		Mockito.when(employeeRepositoryMock.save(employee)).thenReturn(employee);
		
		employeeServiceSpy.createEmployee(employeeDTO);
		
		Assert.assertNotNull(employeeDTO);
	}
	
	@Test
	public void updateEmployee() {
		Position position = new Position();
		position.setId(1L);
		Optional<Position> optionalPosition = Optional.of(position);
		Mockito.when(positionRepositoryMock.findById(1L)).thenReturn(optionalPosition);
		
		Person person = new Person();
		person.setId(1L);
		Optional<Person> optionalPerson = Optional.of(person);
		Mockito.when(personRepositoryMock.findById(1L)).thenReturn(optionalPerson);
		
		Employee employee = new Employee();
		employee.setId(1L);
		employee.setPerson(person);
		employee.setPosition(position);
		Optional<Employee> optionalEmployee = Optional.of(employee);
		Mockito.when(employeeRepositoryMock.findById(1L)).thenReturn(optionalEmployee);
		
		Mockito.when(personRepositoryMock.save(person)).thenReturn(person);
		Mockito.when(employeeRepositoryMock.save(employee)).thenReturn(employee);
		
		employeeServiceSpy.updateEmployee(employeeDTO);
		
		Assert.assertNotNull(employeeDTO);
	}
	
	@Test
	public void thereIsNoEmployeeOnUpdate() {
		Exception exception = assertThrows(NotFoundRequestException.class, () -> {
			Optional<Employee> optionalEmployee = Optional.ofNullable(null);
			Mockito.when(employeeRepositoryMock.findById(1L)).thenReturn(optionalEmployee);
			
			employeeServiceSpy.updateEmployee(employeeDTO);
		});
		
		String expectedMessage = "The employee doesn't exist in the database";
	    String actualMessage = exception.getMessage();
	    assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	public void thereIsNoPositionOnUpdate() {
		Exception exception = assertThrows(NotFoundRequestException.class, () -> {
			Employee employee = new Employee();
			employee.setId(1L);
			Optional<Employee> optionalEmployee = Optional.of(employee);
			Mockito.when(employeeRepositoryMock.findById(1L)).thenReturn(optionalEmployee);
			
			Optional<Position> optionalPosition = Optional.ofNullable(null);
			Mockito.when(positionRepositoryMock.findById(1L)).thenReturn(optionalPosition);
			
			employeeServiceSpy.updateEmployee(employeeDTO);
		});
		
		String expectedMessage = "The position doesn't exist in the database";
	    String actualMessage = exception.getMessage(); 
	    assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test()
	public void deleteEmployee() {
		Person person = new Person();
		person.setId(1L);
		
		Employee employee = new Employee();
		employee.setId(1L);
		employee.setPerson(person);
		
		Optional<Employee> optionalEmployee = Optional.of(employee);
		Mockito.when(employeeRepositoryMock.findById(1L)).thenReturn(optionalEmployee);
		
		Optional<Person> optionalPerson = Optional.of(person);
		Mockito.when(personRepositoryMock.findById(employee.getPerson().getId())).thenReturn(optionalPerson);
		
		employeeRepositoryMock.delete(employee);
		personRepositoryMock.delete(person);
		
		employeeServiceSpy.deleteEmployee(employeeDTO.getId());
	}
	
	@Test
	public void thereIsNoEmployeeOnDelete() {
		Exception exception = assertThrows(NotFoundRequestException.class, () -> {
			Optional<Employee> optionalEmployee = Optional.ofNullable(null);
			Mockito.when(employeeRepositoryMock.findById(1L)).thenReturn(optionalEmployee);
			employeeServiceSpy.deleteEmployee(employeeDTO.getId());
		});
		
		String expectedMessage = "The employee doesn't exist in the database";
	    String actualMessage = exception.getMessage();
	    assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	public void thereIsNoPersonOnDelete() {
		Exception exception = assertThrows(NotFoundRequestException.class, () -> {
			Person person = new Person();
			person.setId(null);
			
			Employee employee = new Employee();
			employee.setId(1L);
			employee.setPerson(person);
			
			Optional<Employee> optionalEmployee = Optional.of(employee);
			Mockito.when(employeeRepositoryMock.findById(1L)).thenReturn(optionalEmployee);
			
			Optional<Person> optionalPerson = Optional.ofNullable(null);
			Mockito.when(personRepositoryMock.findById(employee.getPerson().getId())).thenReturn(optionalPerson);
			
			employeeServiceSpy.deleteEmployee(employeeDTO.getId());
		});
		
		String expectedMessage = "The person doesn't exist in the database";
	    String actualMessage = exception.getMessage(); 
	    assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	public void getEmployeesByName() {
		List<Employee> list = new ArrayList<>();
		FilterDTO filter = new FilterDTO();
		filter.setName("name");
		filter.setPosition("");
		Mockito.when(employeeRepositoryMock.findByName(filter.getName())).thenReturn(list);
		employeeServiceSpy.getEmployees(filter);
	}
	
	@Test
	public void getEmployeesByPosition() {
		List<Employee> list = new ArrayList<>();
		FilterDTO filter = new FilterDTO();
		filter.setName("");
		filter.setPosition("dev");
		Mockito.when(employeeRepositoryMock.findByPosition(filter.getPosition())).thenReturn(list);
		employeeServiceSpy.getEmployees(filter);
	}
	
	@Test
	public void getAllEmployees() {
		List<Employee> list = new ArrayList<>();
		FilterDTO filter = new FilterDTO();
		filter.setName("");
		filter.setPosition("");
		Mockito.when(employeeRepositoryMock.findAll()).thenReturn(list);
		employeeServiceSpy.getEmployees(filter);
	}
    
	@Test
	public void bothParameterNotAllowed() {
		Exception exception = assertThrows(ApiRequestException.class, () -> {
			FilterDTO filter = new FilterDTO();
			filter.setName("name");
			filter.setPosition("dev");
			employeeServiceSpy.getEmployees(filter);
		});
		
		String expectedMessage = "Both parameter are not allowed";
	    String actualMessage = exception.getMessage(); 
	    assertTrue(actualMessage.contains(expectedMessage));
	}
}
