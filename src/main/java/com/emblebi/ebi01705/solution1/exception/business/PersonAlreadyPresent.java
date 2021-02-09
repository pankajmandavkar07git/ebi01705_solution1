package com.emblebi.ebi01705.solution1.exception.business;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.emblebi.ebi01705.solution1.exception.BusinessException;

@ResponseStatus(
	code = HttpStatus.BAD_REQUEST
	, reason = "Person Already Present !!!"
)
public class PersonAlreadyPresent extends BusinessException {

	private static final long serialVersionUID = 1L;

}
