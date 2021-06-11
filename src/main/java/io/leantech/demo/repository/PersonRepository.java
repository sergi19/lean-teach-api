package io.leantech.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.leantech.demo.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
