package com.james.medpay.core.configuration.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.james.medpay.features.users.data.repositoryImpl.UserPrincipalImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Component
public class JwtProvider {

	@Value(value = "${jwt.secret}")
	private String secret;

	@Value(value = "${jwt.expiresIn}")
	private Integer jwtExpiresIn;

	public static final String APP_PREFIX = "PAYMED";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String TOKEN_HEADER = "Authorization";
	public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified";
	public static final String GET_ARRAYS_ISSUER = APP_PREFIX + "_API";
	public static final String GET_USER_GROUP = APP_PREFIX + "_Users";
	public static final String AUTHORITIES = "Authorities";
	public static final int JWT_DEFAULT_EXPIRE_IN = 6; // hours

	public String generateToken(Authentication authentication) {
		User principal = (User) authentication.getPrincipal();

		String[] claims = this.getClaimsFromUser(principal);
		int expireTime = jwtExpiresIn == null ? JWT_DEFAULT_EXPIRE_IN : jwtExpiresIn;
		Date expireDate = DateUtils.addHours(new Date(), expireTime);

		return JWT.create()
				.withIssuer(GET_ARRAYS_ISSUER)
				.withAudience(GET_USER_GROUP)
				.withIssuedAt(new Date())
				.withSubject(principal.getUsername())
				.withArrayClaim(AUTHORITIES, claims)
				.withExpiresAt(expireDate)
				.sign(HMAC512(secret.getBytes()));
	}

	public boolean validateToken(String token, HttpServletRequest httpServletRequest) {
		JWTVerifier verifier = this.getJWTVerifier();
		return StringUtils.isNotEmpty(token) && !this.isTokenExpired(verifier, token);
	}

	// get authentication of verified token
	public Authentication getAuthentication(
			String username, List<GrantedAuthority> authorities,
			HttpServletRequest request) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				null, authorities);
		authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		return authenticationToken;
	}

	public String getUsernameFromJWT(String token) {
		JWTVerifier verifier = this.getJWTVerifier();
		return verifier.verify(token).getSubject();
	}

	// get authorities from user token
	public List<GrantedAuthority> getAuthoritiesFromToken(String token) {
		String[] claims = this.getClaimsFromToken(token);
		return Arrays.stream(claims).map(SimpleGrantedAuthority::new).collect(Collectors.toList());

	}

	// check if token is valid
	public boolean isTokenValid(String username, String token) {
		JWTVerifier verifier = this.getJWTVerifier();
		return StringUtils.isNotEmpty(username) && !this.isTokenExpired(verifier, token);
	}

	// get the subject (username) from the token
	public String getSubjectFromToken(String token) {
		JWTVerifier verifier = this.getJWTVerifier();
		return verifier.verify(token).getSubject();
	}

	private boolean isTokenExpired(JWTVerifier verifier, String token) {
		Date expirationDate = verifier.verify(token).getExpiresAt();
		return expirationDate.before(new Date());
	}

	private JWTVerifier getJWTVerifier() {
		JWTVerifier verifier;
		try {
			Algorithm algorithm = HMAC512(secret);
			verifier = JWT.require(algorithm).withIssuer(GET_ARRAYS_ISSUER).build();
		} catch (JWTVerificationException e) {
			throw new JWTVerificationException(TOKEN_CANNOT_BE_VERIFIED);
		}
		return verifier;
	}

	private String[] getClaimsFromUser(User userPrincipal) {
		return Arrays.stream(userPrincipal.getAuthorities().toArray()).map(Object::toString).toArray(
				String[]::new);
	}

	private String[] getClaimsFromToken(String token) {
		JWTVerifier verifier = this.getJWTVerifier();
		return verifier.verify(token).getClaim(AUTHORITIES).asArray(String.class);
	}

	// get claims (permissions) from user details
	private String[] getClaimsFromUser(UserPrincipalImpl userPrincipal) {
		return Arrays.stream(userPrincipal.getAuthorities().toArray()).map(Object::toString).toArray(
				String[]::new);
	}
}
