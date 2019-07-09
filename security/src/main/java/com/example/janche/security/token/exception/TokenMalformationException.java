package com.example.janche.security.token.exception;

import org.springframework.security.core.AuthenticationException;

public class TokenMalformationException extends AuthenticationException {

	public TokenMalformationException( String msg ){
		super(msg);
		// TODO Auto-generated constructor stub
	}

}
