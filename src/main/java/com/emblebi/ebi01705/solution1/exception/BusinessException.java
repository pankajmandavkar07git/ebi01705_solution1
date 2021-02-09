package com.emblebi.ebi01705.solution1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
	code = HttpStatus.BAD_REQUEST
	, reason = "Business validation failed"
)
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 7817449175782499556L;
	
}