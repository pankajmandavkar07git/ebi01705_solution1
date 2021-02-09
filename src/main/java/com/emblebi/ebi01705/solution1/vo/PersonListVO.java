package com.emblebi.ebi01705.solution1.vo;

import java.util.List;

import org.springframework.hateoas.RepresentationModel;

public class PersonListVO extends RepresentationModel<PersonListVO>{
	
	private List<PersonVO> person;

	public List<PersonVO> getPerson() {
		return person;
	}

	public void setPerson(List<PersonVO> person) {
		this.person = person;
	}
}
