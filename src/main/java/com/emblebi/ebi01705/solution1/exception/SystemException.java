package com.emblebi.ebi01705.solution1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
	code = HttpStatus.INTERNAL_SERVER_ERROR
	, reason = "Something went wrong !!!"
)
public class SystemException extends Exception {
	
	private static final long serialVersionUID = 3090313425312202738L;
	
}