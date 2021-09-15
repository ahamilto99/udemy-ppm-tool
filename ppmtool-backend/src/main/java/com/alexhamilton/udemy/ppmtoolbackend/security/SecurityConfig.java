package com.alexhamilton.udemy.ppmtoolbackend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception { // @formatter:off
		http
			.cors()
			.and()
				.csrf().disable()
				.exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
			.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
				.headers().frameOptions().sameOrigin()	// for H2 console
			.and()
				.authorizeRequests()
					.antMatchers("/", "/favicon.ico", "/**/*.css", "/**/*.js", "/**/*.gif", "/**/*.svg", "/**/*.html", "/**/*.jpg", "/**/*.png",
							"/api/users/**")
									.permitAll()
			.anyRequest()
				.authenticated();
	} // @formatter:on

}
