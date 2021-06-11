package io.leantech.demo.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import io.leantech.demo.dto.PositionDTO;
import io.leantech.demo.model.Employee;
import io.leantech.demo.model.Position;
import io.leantech.demo.repository.PositionRepository;

@RunWith(MockitoJUnitRunner.class)
public class PositionServiceTest {
	
	@Mock
	private ModelMapper modelMapper;
	
	@Mock
	private PositionService positionServiceSpy;
	
	@InjectMocks
	private PositionService positionService;
	
	private PositionRepository positionRepositoryMock;
	
	private PositionDTO positionDTO;
	
	@Before
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		
		positionDTO = new PositionDTO();
		positionDTO.setId(1L);
		positionDTO.setName("dev");
		
		positionRepositoryMock = Mockito.mock(PositionRepository.class);
		
		positionService = new PositionService(positionRepositoryMock, modelMapper);
		positionServiceSpy = Mockito.spy(positionService);
	}
	
	@Test
	public void getPositions() {
		List<Employee> employeeList = new ArrayList<>();
		List<Position> list = new ArrayList<>();
		Position position = new Position();
		position.setEmployees(employeeList);
		Mockito.when(positionRepositoryMock.findAll()).thenReturn(list);
		positionServiceSpy.getPositions();
	}
	
}
