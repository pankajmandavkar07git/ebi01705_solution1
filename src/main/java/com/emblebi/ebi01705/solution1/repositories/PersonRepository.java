package com.emblebi.ebi01705.solution1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.emblebi.ebi01705.solution1.entities.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>, PagingAndSortingRepository<Person, Long> {

	Person findByFirstName(String firstName);
}