package com.james.medpay.core.configuration.config;

import com.james.medpay.core.configuration.security.JwtAuthenticationEntryPoint;
import com.james.medpay.core.configuration.security.JwtAuthenticationFilter;
import com.james.medpay.features.users.data.repositoryImpl.UserDetailsRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.james.medpay.core.constant.Routes.API_PREFIX;

/*
securedEnabled = true enables @Secured annotation.
jsr250Enabled = true enables @RolesAllowed annotation.
prePostEnabled = true enables @PreAuthorize, @PostAuthorize, @PreFilter, @PostFilter annotations.
*/

@Configuration
@EnableScheduling
@EnableWebSecurity
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)

public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String[] SWAGGER_WHITELIST = {
			"/authenticate",
			"/swagger-resources/**",
			"/swagger-ui/**",
			"swagger-ui.html",
			"/swagger.**",
			"/v3/api-docs",
			"/webjars/**"
	};

	private static final String[] UI_WHITELIST = {
			"/",
			"/**.css",
			"/**.js", "/**.png", "/**.jpg", "/**.jpeg", "/**.svg", "**.svg",
			"**.jpg",
			"/static/**", "/assets/**"
	};

	private static final String[] AUTH_WHITELIST = {
			API_PREFIX + "/auth/login",
	};

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter(getUserDetails());
	}

	@Bean
	public UserDetailsService getUserDetails() {
		// Implementation class
		return new UserDetailsRepositoryImpl();
	}

	@Bean
	public AuditorAware<String> auditorAware() {
		return new AuditorAwareImpl();
	}

	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable();
		http.headers().frameOptions().sameOrigin().and();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();
		http.exceptionHandling().authenticationEntryPoint(new JwtAuthenticationEntryPoint()).and();
		http.authorizeRequests().requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll();
		http.authorizeRequests().antMatchers(SWAGGER_WHITELIST).permitAll();
		http.authorizeRequests().antMatchers(UI_WHITELIST).permitAll();
		http.authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.GET,"swagger-ui.html").permitAll();
		http.authorizeRequests().anyRequest().authenticated();
		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	public void configure(WebSecurity web) {
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(getUserDetails()).passwordEncoder(passwordEncoder());
	}
}
