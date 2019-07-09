package com.example.janche.security.token.exception;

import org.springframework.security.core.AuthenticationException;

public class TokenExpiredException extends AuthenticationException {

	public TokenExpiredException( String msg ){
		super(msg);
		// TODO Auto-generated constructor stub
	}

}
