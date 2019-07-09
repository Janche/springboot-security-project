package com.example.janche.security.token.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import javax.security.auth.Subject;
import java.util.Collection;

public class JWTAuthenticationToken extends AbstractAuthenticationToken {

	/**
	 * serialVersionUID
	 */
	public String refresh_token;



	public JWTAuthenticationToken(String refresh_token){
		super(null);
		this.refresh_token = refresh_token;
	}

	public JWTAuthenticationToken(String refresh_token, Collection<? extends GrantedAuthority> authorities ){
		super(authorities);
		this.refresh_token = refresh_token;
	}






	public String getRefresh_token() {
		return refresh_token;
	}

	public void setRefresh_token( String refresh_token ) {
		this.refresh_token = refresh_token;
	}

	@Override
	public boolean equals( Object obj ) {
		if (!(obj instanceof JWTAuthenticationToken)) {
			return false;
		}

		JWTAuthenticationToken test = (JWTAuthenticationToken) obj;

		return test.getRefresh_token().equals(refresh_token);
	}




	@Override
	public Object getCredentials() {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public Object getPrincipal() {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public boolean implies( Subject subject ) {
		// TODO Auto-generated method stub
		return false;
	}

}
