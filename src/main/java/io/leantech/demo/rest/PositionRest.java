package io.leantech.demo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.leantech.demo.dto.PositionDTO;
import io.leantech.demo.services.PositionService;

@RestController
@RequestMapping(value = "/position")
public class PositionRest {

	private PositionService positionService;
	
	@Autowired
	public PositionRest(PositionService positionService) {
		this.positionService = positionService;
	}
	
	@GetMapping
	public ResponseEntity<List<PositionDTO>> getPositions() {
		return ResponseEntity.ok(positionService.getPositions());
	}
	
}
