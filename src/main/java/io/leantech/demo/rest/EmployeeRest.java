package io.leantech.demo.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.leantech.demo.dto.EmployeeDTO;
import io.leantech.demo.dto.FilterDTO;
import io.leantech.demo.exception.ApiRequestException;
import io.leantech.demo.services.EmployeeService;
import io.leantech.demo.util.ResponseObject;

@RestController
@RequestMapping("/employee")
public class EmployeeRest {

	private EmployeeService employeeService;
	
	@Autowired
	public EmployeeRest(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	@PostMapping
	public ResponseObject createEmlpoyee(@RequestBody EmployeeDTO dto) {
		EmployeeDTO employeeDTO = employeeService.createEmployee(dto);
		ResponseObject response = new ResponseObject(employeeDTO, 
				employeeDTO != null ? "The employee has been registered!" : "It was not possible to register the employee");
		return response;
	}
	
	@PutMapping
	public ResponseObject updateEmlpoyee(@RequestBody EmployeeDTO dto) {
		if (!Optional.ofNullable(dto.getId()).isPresent()) {
			throw new ApiRequestException("The employee id is required");
		}
		
		if (!Optional.ofNullable(dto.getPosition().getId()).isPresent()) {
			throw new ApiRequestException("The position id is required");
		}
		
		EmployeeDTO employeeDTO = employeeService.updateEmployee(dto);
		ResponseObject response = new ResponseObject(employeeDTO, 
				employeeDTO != null ? "The employee has been updated!" : "It was not possible to update the employee");
		return response;
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseObject deleteEmployee(@PathVariable Long id) {
		employeeService.deleteEmployee(id);
		ResponseObject response = new ResponseObject(null, "The employee has been deleted!");
		return response;
	}
	
	@PostMapping(value = "/list")
	public ResponseObject getEmployees(@RequestBody FilterDTO filterDTO) {
		if (!Optional.ofNullable(filterDTO.getName()).isPresent() || 
			!Optional.ofNullable(filterDTO.getPosition()).isPresent()) {
			throw new ApiRequestException("Both parameters must to be sent, even if they are empty");
		}
		
		List<EmployeeDTO> list = employeeService.getEmployees(filterDTO);
		ResponseObject response = new ResponseObject(list, 
				!list.isEmpty() ? list.size() + " results found" : "No results found");
		return response;
	}
	
}
