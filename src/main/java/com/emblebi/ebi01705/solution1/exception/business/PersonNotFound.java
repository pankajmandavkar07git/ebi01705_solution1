package com.emblebi.ebi01705.solution1.exception.business;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.emblebi.ebi01705.solution1.exception.BusinessException;

@ResponseStatus(
	code = HttpStatus.NOT_FOUND
	, reason = "Person Not Found"
)
public class PersonNotFound extends BusinessException {

	private static final long serialVersionUID = 1L;

}
