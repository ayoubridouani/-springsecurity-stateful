package com.youssfi.springboot.stateful.filters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class SimpleAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException{
		//Authentication au = SecurityContextHolder.getContext().getAuthentication();
		//System.out.println(au.isAuthenticated());
		
		//Authentication au = new TestingAuthenticationToken("u","p");
		//System.out.println(au.isAuthenticated());
		//au.setAuthenticated(true);
		//SecurityContextHolder.getContext().setAuthentication(au);
		//System.out.println(au.isAuthenticated());
		
		super.setUsernameParameter("uname");
		String username = obtainUsername(request);
		String password = obtainPassword(request);
		
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
		Authentication au = this.getAuthenticationManager().authenticate(authRequest);		
		System.out.println(au.isAuthenticated());

		return au;
  }
}
