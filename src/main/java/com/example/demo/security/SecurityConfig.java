package com.example.demo.security;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
public class SecurityConfig {

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector)
			throws Exception {
		MvcRequestMatcher.Builder mvcRequestMatcher = new MvcRequestMatcher.Builder(introspector);
		http.csrf(csrf -> csrf.disable()).cors(Customizer.withDefaults())
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(new DemoHeaderRequestMatcher()).permitAll()
						.requestMatchers(mvcRequestMatcher.pattern("/api/login/validate")).permitAll()
						.requestMatchers(mvcRequestMatcher.pattern("/api/admin/validate")).permitAll()
						.requestMatchers(mvcRequestMatcher.pattern("/api/guidelines/*")).permitAll()
						.requestMatchers(mvcRequestMatcher.pattern("/")).permitAll()
						.anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowCredentials(true);
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000","http://speediqfrontend.s3-website-ap-southeast-2.amazonaws.com/"));
		configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
		configuration.setExposedHeaders(List.of("Content-Type"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		configuration.setMaxAge(3600L);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
