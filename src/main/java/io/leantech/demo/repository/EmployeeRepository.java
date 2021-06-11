package io.leantech.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.leantech.demo.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	@Query("SELECT e FROM Employee e WHERE e.person.name = ?1")
	public List<Employee> findByName(String name);
	
	@Query("SELECT e FROM Employee e WHERE e.position.name = ?1")
	public List<Employee> findByPosition(String position);
	
}
