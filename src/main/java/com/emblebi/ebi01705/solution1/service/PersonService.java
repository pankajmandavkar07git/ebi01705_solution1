package com.emblebi.ebi01705.solution1.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.emblebi.ebi01705.solution1.entities.Colour;
import com.emblebi.ebi01705.solution1.entities.Person;
import com.emblebi.ebi01705.solution1.exception.business.PersonAlreadyPresent;
import com.emblebi.ebi01705.solution1.exception.business.PersonNotFound;
import com.emblebi.ebi01705.solution1.repositories.ColourRepository;
import com.emblebi.ebi01705.solution1.repositories.PersonRepository;
import com.emblebi.ebi01705.solution1.util.PersonUtils;
import com.emblebi.ebi01705.solution1.vo.PersonVO;

@Service
public class PersonService {

	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private ColourRepository colourRepository;
	
	private final Logger logger = LogManager.getLogger(PersonService.class);

	public List<PersonVO> findAll(Integer page, Integer size) {
		
		if(page!=null) {
			if(size==null || size.intValue()==0) {
				size = 10;
			}
			Pageable sortedById = PageRequest.of(page, size, Sort.by("id"));
			
			Page<Person> persons = this.personRepository.findAll(sortedById);
					
			return PersonUtils.copyPropertiesToList(persons.get().collect(Collectors.toList()));
		}
		else {
			return PersonUtils.copyPropertiesToList(this.personRepository.findAll(Sort.by("id")));
		}
	}
	
	public Person findByFirstName(String firstName) {
		
		Person person = this.personRepository.findByFirstName(firstName);
				
		if(ObjectUtils.isEmpty(person)) {
			throw new PersonNotFound();
		}
		return person;
	}

	public PersonVO findById(Long id) {
		
		Optional<Person> optionalPerson = this.personRepository.findById(id);
				
		if(optionalPerson.isEmpty()) {
			throw new PersonNotFound();
		}
		return PersonUtils.copyProperties(optionalPerson.get());
	}

	public PersonVO save(PersonVO personVO) {

		Person person = PersonUtils.copyProperties(personVO);
		Colour colour = this.colourRepository.findByName(person.getFavouriteColour().getName());
		if(!ObjectUtils.isEmpty(colour)) {
			person.setFavouriteColour(colour);
		}
		else {
			person.setFavouriteColour(this.colourRepository.save(person.getFavouriteColour()));
		}
		
		try {
			person = this.personRepository.save(person);
			logger.info("new person object added with id %s", person.getId());
		}
		catch(DataIntegrityViolationException e) {
			
			logger.error(e);
			throw new PersonAlreadyPresent();
		}
		
		return PersonUtils.copyProperties(person);
	}

	public PersonVO update(Long id, PersonVO personVO) {

		PersonVO existingPersonVO = this.findById(id);
		
		Person person = PersonUtils.copyProperties(personVO);
		Colour colour = colourRepository.findByName(person.getFavouriteColour().getName());
		if(!ObjectUtils.isEmpty(colour)) {
			person.getFavouriteColour().setId(colour.getId());
		}
		person.setId(existingPersonVO.getId());
		person.setFavouriteColour(this.colourRepository.save(person.getFavouriteColour()));
		
		PersonVO personVOOutput = PersonUtils.copyProperties(personRepository.save(person));
		logger.info("updated details for person object id %s", person.getId());
		
		return personVOOutput;
	}

	public void deleteById(Long id) {
		
		this.findById(id);
		personRepository.deleteById(id);
	}
}
