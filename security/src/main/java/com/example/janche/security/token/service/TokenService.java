package com.example.janche.security.token.service;

import com.example.janche.security.token.util.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * token操作
 * @author  daiyp
 * @date 2018-9-26
 */

@Component
public class TokenService {

	@Value("janche.auth.secret")
	private String secret;

	public void init(String secret){
		this.secret=secret;
	}

	/**
	 *
	 * 功能：生成 jwt token<br/>
	 * @param name	实例名
	 * @param param	需要保存的参数
	 * @param expirationtime	过期时间(单位:分钟)
	 * @return
	 * @exception   无
	 *
	 */
	public String sign(String name,Map<String,Object> param,long expirationminutes){
		return JwtUtil.sign(name, param, secret, expirationminutes*60*1000);
	}
	/**
	 *
	 * 功能：从token中获取数据<br/>
	 * @param jwt token
	 * @param key	需要获取的key
	 * @return
	 * @exception   无
	 *
	 */
	public Object getValueFromToken(String jwt,String key){
		return JwtUtil.verify(jwt, secret).get(key);
	}

}
