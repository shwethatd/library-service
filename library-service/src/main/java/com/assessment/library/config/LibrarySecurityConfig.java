package com.assessment.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@SuppressWarnings("deprecation")
@Configuration
public class LibrarySecurityConfig extends WebSecurityConfigurerAdapter {

	/*
	 * @Override protected void configure(AuthenticationManagerBuilder auth) throws
	 * Exception {
	 * 
	 * auth.inMemoryAuthentication().withUser("user").password("password").roles(
	 * "USER").and().withUser("admin") .password("password").roles("USER", "ADMIN");
	 * 
	 * }
	 */

	// Secure the endpoins with HTTP Basic authentication
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
				// HTTP Basic authentication
				.httpBasic().and().authorizeRequests()
				.antMatchers(HttpMethod.GET, "/api/book/**").hasRole("USER")
				.antMatchers(HttpMethod.POST, "/api/book").hasRole("USER")
				.antMatchers(HttpMethod.PATCH, "/api/book/name/**").hasRole("USER")
				.antMatchers(HttpMethod.PATCH, "/api/book/author/**").hasRole("ADMIN")
				.antMatchers(HttpMethod.DELETE, "/api/book/**").hasRole("ADMIN")
				.and()
				.csrf()
				.disable()
				.formLogin()
				.disable();
	}

	@Bean
	public UserDetailsService userDetailsService() { // ok for demo
		User.UserBuilder users = User.withDefaultPasswordEncoder();
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		manager.createUser(users.username("user").password("password").roles("USER").build());
		manager.createUser(users.username("admin").password("password").roles("USER", "ADMIN").build());
		return manager;
	}

}
