package com.james.medpay.core.configuration.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.james.medpay.core.configuration.security.JwtProvider.TOKEN_HEADER;
import static com.james.medpay.core.configuration.security.JwtProvider.TOKEN_PREFIX;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
	@Autowired
	private JwtProvider jwtProvider;

	private final UserDetailsService userDetailsService;

	public JwtAuthenticationFilter(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {
		try {
			String jwt = this.getJwtFromRequest(request);
			if (StringUtils.isNotEmpty(jwt) && jwtProvider.validateToken(jwt, request)) {
				String username = jwtProvider.getUsernameFromJWT(jwt);
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			filterChain.doFilter(request, response);
		} catch (Exception e) {
			request.setAttribute("exception", e.getMessage());
			response.sendError(HttpStatus.UNAUTHORIZED.value());
		}
	}

	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader(TOKEN_HEADER);
		if (StringUtils.isNotEmpty(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
			return bearerToken.substring(TOKEN_PREFIX.length());
		}
		return bearerToken;
	}
}
