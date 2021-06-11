package io.leantech.demo.services;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.leantech.demo.dto.PositionDTO;
import io.leantech.demo.model.Employee;
import io.leantech.demo.model.Position;
import io.leantech.demo.repository.PositionRepository;

@Service
public class PositionService {
	
	private PositionRepository positionRepository;
	
	private ModelMapper modelMapper;
	
	@Autowired
	public PositionService(PositionRepository positionRepository, ModelMapper modelMapper) {
		this.positionRepository = positionRepository;
		this.modelMapper = modelMapper;
	}
	 
	
	public List<PositionDTO> getPositions() {
		List<Position> list = positionRepository.findAll();
		for (Position position : list) {
			List<Employee> orderedListBySalary = position.getEmployees()
					.stream()
					.sorted(Comparator.comparing(Employee::getSalary).reversed())
					.collect(Collectors.toList());
			position.setEmployees(orderedListBySalary);
		}
		
		List<PositionDTO> dtoList = list
				.stream()
				.map(position -> modelMapper.map(position, PositionDTO.class))
				.collect(Collectors.toList());
		return dtoList;
	}

}
