// src/main/java/com/riya/feedback/manager/config/SecurityConfig.java
package com.riya.feedback.manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// 1. Define In-Memory User for the 'ADMIN' Role
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("admin") // Username for the administrator
				.password("password123") // The password for local testing
				.roles("ADMIN"); // Grant the 'ADMIN' role
	}

	// 2. Configure Endpoint Authorization
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				// Allow public access to the submission form and success pages
				.antMatchers("/", "/submit", "/submission-success", "/h2-console/**").permitAll()
				// All paths under /admin/ require the user to have the 'ADMIN' role
				.antMatchers("/admin/**").hasRole("ADMIN")
				// Any other request must be authenticated (logged in)
				.anyRequest().authenticated().and().formLogin() // Use the default Spring Security login page
				.permitAll().and().logout() // Enhanced Logout Configuration
				.logoutSuccessUrl("/") // Redirect to home page after successful logout
				.permitAll().and()
				// ADDED: Prevent Caching of Secured Pages (Fixes back button issue)
				.headers().cacheControl().disable().and()
				// CRITICAL for H2 Console: H2 runs in a frame, so frame protection must be
				// disabled
				.headers().frameOptions().disable().and()
				// CRITICAL for Thymeleaf forms: Disable CSRF for simplified testing
				.csrf().disable();
	}

	// 3. Define the Password Encoder
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
}