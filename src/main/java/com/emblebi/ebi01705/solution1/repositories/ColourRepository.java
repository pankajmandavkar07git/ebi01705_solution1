package com.emblebi.ebi01705.solution1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emblebi.ebi01705.solution1.entities.Colour;

@Repository
public interface ColourRepository extends JpaRepository<Colour, Long> {

	Colour findByName(String name);
}