package com.emblebi.ebi01705.solution1.util;

import java.util.List;
import java.util.stream.Collectors;

import com.emblebi.ebi01705.solution1.entities.Colour;
import com.emblebi.ebi01705.solution1.entities.Person;
import com.emblebi.ebi01705.solution1.vo.PersonVO;

public interface PersonUtils {
	
	public static PersonVO copyProperties(Person person) {
		
		PersonVO personVO = new PersonVO();
		
		personVO.setId(person.getId());
		personVO.setFirstName(person.getFirstName());
		personVO.setLastName(person.getLastName());
		personVO.setAge(person.getAge());
		personVO.setFavouriteColour(person.getFavouriteColour().getName());
		
		return personVO;
	}
	
	public static Person copyProperties(PersonVO personVO) {
		
		Person person = new Person();
		
		person.setId(personVO.getId());
		person.setFirstName(personVO.getFirstName());
		person.setLastName(personVO.getLastName());
		person.setAge(personVO.getAge());
		Colour favouriteColour = new Colour();
		favouriteColour.setName(personVO.getFavouriteColour());
		person.setFavouriteColour(favouriteColour);
		
		return person;
	}
	
	public static List<PersonVO> copyPropertiesToList(List<Person> person) {
		
		return person.stream()
				.map(PersonUtils::copyProperties)
				.collect(Collectors.toList());
	}
}
