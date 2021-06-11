package io.leantech.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.leantech.demo.model.Position;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {

	public Optional<Position> findByName(String name);
	
}
