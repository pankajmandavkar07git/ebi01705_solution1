package com.emblebi.ebi01705.solution1.webresource;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emblebi.ebi01705.solution1.service.PersonService;
import com.emblebi.ebi01705.solution1.vo.PersonVO;

@RestController
@RequestMapping(
	path = "/person"
)
@Validated
public class PersonResource {
	
	@Autowired
	private PersonService personService;
	
	@GetMapping
	public ResponseEntity<List<PersonVO>> findAll() {
 
		List<PersonVO> persons = personService.findAll();
		persons.forEach(e -> e.add(linkTo(methodOn(PersonResource.class).findById(e.getId())).withSelfRel()));
		
		return new ResponseEntity<>(persons, HttpStatus.OK);
	}
	
	@GetMapping(
		value = "/{id}"
	)
	public ResponseEntity<PersonVO> findById(@PathVariable("id") Long id) {
 
		PersonVO personVO = personService.findById(id);
		personVO.add(linkTo(methodOn(PersonResource.class).findById(personVO.getId())).withSelfRel());
		return new ResponseEntity<>(personVO, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<PersonVO> addPerson(@RequestBody @NotNull @Valid PersonVO personVO) {
		
		personVO = personService.save(personVO);
		personVO.add(linkTo(methodOn(PersonResource.class).findById(personVO.getId())).withSelfRel());
		return new ResponseEntity<>(personVO, HttpStatus.CREATED); 
	}
	
	@PutMapping(
		value = "/{id}"
	)
	public ResponseEntity<PersonVO> update(@PathVariable("id") Long id, @RequestBody @NotNull @Valid PersonVO personVO) {
  
		personVO = personService.update(id, personVO);
		personVO.add(linkTo(methodOn(PersonResource.class).findById(personVO.getId())).withSelfRel());
		return new ResponseEntity<>(personVO, HttpStatus.ACCEPTED); 
	}

	@DeleteMapping(
		value = "/{id}"
	)
	public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
  
		personService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.ACCEPTED); 
	}
}
