package com.example.demo.security;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtHelper;

//	@Autowired
//	private UserDetailsService userDetailsService;

	private static final String headerName = "Authorization";
	private static final String tokenSuffix = "Bearer";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String requestUri = request.getRequestURI();

		log.info("Request URI =  {}", requestUri);

		if ("/api/login/validate".equals(requestUri) || "/api/admin/validate".equals(requestUri)
				|| "/".equals(requestUri)) {
			filterChain.doFilter(request, response);
			return;
		}

		log.info(" Request header for demo is {} ", request.getHeader("Demo"));

		log.info(" Request header for Bearer is {} ", request.getHeader(headerName));

		if (request.getHeader("Demo").equals("true")) {
			filterChain.doFilter(request, response);
			return;
		}

		String requestHeader = request.getHeader(headerName);
		log.debug("Header : {}", requestHeader);
		String username = null;
		String token = null;
		if (requestHeader != null && requestHeader.startsWith(tokenSuffix)) {
			token = requestHeader.substring(7);
//			try {
			username = jwtHelper.extractUsername(token);
			log.debug("username from JwtAuthenticationFilter : {}", username);
			System.out.println("username from JwtAuthenticationFilter : " + username);
//			} catch (IllegalArgumentException e) {
//				log.error("Illegal Argument while fetching the username!!");
//				e.printStackTrace();
//				handleException(response, HttpServletResponse.SC_FORBIDDEN, "Access Denied : Illegal Argument");
//				return;
//			} catch (ExpiredJwtException e) {
//				log.error("Given jwt token is expired!!");
//				e.printStackTrace();
//				handleException(response, 440, "Access Denied : Session expired. Please login again.");
//				return;
//			} catch (MalformedJwtException e) {
//				log.error("Some change has done in token !! Invalid Token");
//				e.printStackTrace();
//				handleException(response, HttpServletResponse.SC_FORBIDDEN, "Access Denied : Invalid Token!!");
//				return;
//			} catch (Exception e) {
//				log.error(e.getMessage());
//				e.printStackTrace();
//				return;
//			}

		} else {
			log.info("{} : Invalid Header Value!!", username);
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			if (jwtHelper.validateToken(token)) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null,
						null);
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}

		filterChain.doFilter(request, response);
	}

	private void handleException(HttpServletResponse response, int statusCode, String message) throws IOException {
		response.setStatus(statusCode);
		response.setContentType("application/json");
		PrintWriter writer = response.getWriter();
		writer.println(message);
		writer.flush();
	}

}
