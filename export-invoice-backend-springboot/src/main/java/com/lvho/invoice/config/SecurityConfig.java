package com.lvho.invoice.config;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.context.annotation.Bean; 
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager; 
import org.springframework.security.authentication.AuthenticationProvider; 
import org.springframework.security.authentication.dao.DaoAuthenticationProvider; 
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration; 
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity; 
import org.springframework.security.config.annotation.web.builders.HttpSecurity; 
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity; 
import org.springframework.security.config.http.SessionCreationPolicy; 
import org.springframework.security.core.userdetails.UserDetailsService; 
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; 
import org.springframework.security.crypto.password.PasswordEncoder; 
import org.springframework.security.web.SecurityFilterChain; 
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.lvho.invoice.entity.Role;
import com.lvho.invoice.security.JwtFilter;
import com.lvho.invoice.service.UserInfoService; 

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig { 

	@Autowired
	private JwtFilter jwtFilter; 

	@Bean
	public UserDetailsService userDetailsService() { 
		return new UserInfoService(); 
	} 

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { 

		return http.csrf(csrf -> csrf.disable())
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(requests -> requests.requestMatchers(jwtFilter.getIgnoreCsrfAntMatchers()).permitAll())

				.authorizeHttpRequests(requests -> requests.requestMatchers(HttpMethod.POST, "/register").hasAnyAuthority(Role.SYS_ADMIN.toString()))

				.authorizeHttpRequests(requests -> requests.requestMatchers(HttpMethod.GET, "/employee").hasAnyAuthority(Role.SYS_ADMIN.toString(), Role.ADMIN.toString()))
				.authorizeHttpRequests(requests -> requests.requestMatchers(HttpMethod.POST, "/employee").hasAnyAuthority(Role.SYS_ADMIN.toString(), Role.ADMIN.toString()))
				.authorizeHttpRequests(requests -> requests.requestMatchers(HttpMethod.DELETE, "/employee").hasAnyAuthority(Role.SYS_ADMIN.toString(), Role.ADMIN.toString()))
				.authorizeHttpRequests(requests -> requests.requestMatchers(HttpMethod.PUT, "/employee").hasAnyAuthority(Role.SYS_ADMIN.toString(), Role.ADMIN.toString()))

				.authorizeHttpRequests(requests -> requests.requestMatchers(HttpMethod.GET, "/project").hasAnyAuthority(Role.SYS_ADMIN.toString(), Role.ADMIN.toString()))
				.authorizeHttpRequests(requests -> requests.requestMatchers(HttpMethod.POST, "/project").hasAnyAuthority(Role.SYS_ADMIN.toString(), Role.ADMIN.toString()))
				.authorizeHttpRequests(requests -> requests.requestMatchers(HttpMethod.DELETE, "/project").hasAnyAuthority(Role.SYS_ADMIN.toString(), Role.ADMIN.toString()))
				.authorizeHttpRequests(requests -> requests.requestMatchers(HttpMethod.PUT, "/project").hasAnyAuthority(Role.SYS_ADMIN.toString(), Role.ADMIN.toString()))

				.authorizeHttpRequests(requests -> requests.requestMatchers(HttpMethod.GET, "/purchase-order").hasAnyAuthority(Role.SYS_ADMIN.toString(), Role.ADMIN.toString()))
				.authorizeHttpRequests(requests -> requests.requestMatchers(HttpMethod.POST, "/purchase-order").hasAnyAuthority(Role.SYS_ADMIN.toString()))
				.authorizeHttpRequests(requests -> requests.requestMatchers(HttpMethod.DELETE, "/purchase-order").hasAnyAuthority(Role.SYS_ADMIN.toString()))
				.authorizeHttpRequests(requests -> requests.requestMatchers(HttpMethod.PUT, "/purchase-order").hasAnyAuthority(Role.SYS_ADMIN.toString()))

				.authorizeHttpRequests(requests -> requests.requestMatchers("/employee/**").hasAnyAuthority(Role.SYS_ADMIN.toString(), Role.ADMIN.toString()))
				.authorizeHttpRequests(requests -> requests.requestMatchers("/project/**").hasAnyAuthority(Role.SYS_ADMIN.toString(), Role.ADMIN.toString()))

				.authorizeHttpRequests(requests -> requests.requestMatchers("/purchase-order/**").hasAnyAuthority(Role.SYS_ADMIN.toString()))

                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .build(); 
	} 

	@Bean
	public static PasswordEncoder passwordEncoder() { 
		return new BCryptPasswordEncoder(); 
	} 

	@Bean
	public AuthenticationProvider authenticationProvider() { 
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(); 
		authenticationProvider.setUserDetailsService(userDetailsService()); 
		authenticationProvider.setPasswordEncoder(passwordEncoder()); 
		return authenticationProvider; 
	} 

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception { 
		return config.getAuthenticationManager(); 
	}
} 
