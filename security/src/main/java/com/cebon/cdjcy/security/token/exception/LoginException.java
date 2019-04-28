package com.cebon.cdjcy.security.token.exception;

import org.springframework.security.core.AuthenticationException;

public class LoginException extends AuthenticationException {

	public LoginException( String msg ){
		super(msg);
		// TODO Auto-generated constructor stub
	}

}
