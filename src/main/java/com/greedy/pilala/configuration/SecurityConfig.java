package com.greedy.pilala.configuration;

import com.greedy.pilala.jwt.JwtAccessDeniedHandler;
import com.greedy.pilala.jwt.JwtAuthenticationEntryPoint;
import com.greedy.pilala.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

	// 인증 실패 핸들러
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	// 인가 실패 핸들러
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
	// 토큰 제공 클래스
	private final TokenProvider tokenProvider;

	public SecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
						  JwtAccessDeniedHandler jwtAccessDeniedHandler,
						  TokenProvider tokenProvider) {
		this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
		this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
		this.tokenProvider = tokenProvider;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

		return http
				.csrf()
					.disable()
				.exceptionHandling()
					.authenticationEntryPoint(jwtAuthenticationEntryPoint)
					.accessDeniedHandler(jwtAccessDeniedHandler)
				.and()
					.sessionManagement()
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
					.authorizeRequests()
					.antMatchers(HttpMethod.OPTIONS,"/**").permitAll()
					.antMatchers("/auth/**").permitAll()
					.antMatchers("/api/v1/classes/**").permitAll()
					.antMatchers("/api/**").hasAnyRole("USER", "MEMBER", "ADMIN")
				.and()
					.cors()
				.and()
					.apply(new JwtSecurityConfig(tokenProvider))
				.and()
					.build();

	}

}




