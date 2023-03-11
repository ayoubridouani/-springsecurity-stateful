package com.youssfi.springboot.stateful.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.youssfi.springboot.stateful.filters.SimpleAuthenticationFilter;
import com.youssfi.springboot.stateful.handlers.LoginAuthenticationSuccessHandler;


@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter{
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private LoginAuthenticationSuccessHandler successHandler;
		
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// method 1: using the default config
		//super.configure(auth);
		
		// method 2: using MEMORY authentication
		/*auth.inMemoryAuthentication().withUser("create").password("{noop}0").roles("CREATE");
		auth.inMemoryAuthentication().withUser("edit").password("{noop}0").roles("EDIT");
		auth.inMemoryAuthentication().withUser("admin").password("{noop}0").roles("ADMIN");*/
		//-----------------------------------------------------------------------------------------------------
		/*auth.inMemoryAuthentication().withUser("create").password(passwordEncoder.encode("0")).roles("CREATE");
		auth.inMemoryAuthentication().withUser("edit").password(passwordEncoder.encode("0")).roles("EDIT");
		auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder.encode("0")).roles("ADMIN");*/
		
		// method 3: using JDBC authentication
		/*auth.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery("select username as principal, password as credentials, 0 from APP_USER where username=?")
		.authoritiesByUsernameQuery("select u.username as principal, r.name as role from APP_USER u, APP_ROLE r, APP_USER_ROLES ur where username=? and ur.APP_USER_ID=u.id and ur.ROLES_ID=r.id")
		.passwordEncoder(passwordEncoder)
		.rolePrefix("ROLE_");*/
		
		// method 4: using userDetailsService with bCrypt default password encoder
		//auth.userDetailsService(userDetailsService);
		
		// method 5: using authenticationProvider (same of userDetailsService method just this method have more options)
		auth.authenticationProvider(authProvider());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.addFilter(authenticationFilter());
		http.csrf().disable();
		http.authorizeRequests().antMatchers("/user/**").hasAuthority("USER");
		http.authorizeRequests().antMatchers("/admin/**").hasAuthority("ADMIN");
		http.authorizeRequests().antMatchers("/h2-console/**").permitAll();
		http.formLogin().usernameParameter("uname");
		http.authorizeRequests().anyRequest().authenticated();//.permitAll();
		http.headers().frameOptions().disable();
	}
	
	public AuthenticationProvider authProvider() {
	    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
	    provider.setUserDetailsService(userDetailsService);
	    provider.setPasswordEncoder(passwordEncoder);
	    return provider;
	}
	
	public SimpleAuthenticationFilter authenticationFilter() throws Exception {
	    SimpleAuthenticationFilter filter = new SimpleAuthenticationFilter();
	    filter.setAuthenticationManager(authenticationManager());
	    filter.setAuthenticationSuccessHandler(successHandler);
	    return filter;
	}
}
