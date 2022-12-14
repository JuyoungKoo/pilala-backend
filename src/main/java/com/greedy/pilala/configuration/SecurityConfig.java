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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

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

	/* CORS(cross-origin-resource-sharing) : 교차 출처 자원 공유
	 * 예전에는 자원 저장 서버와 웹 페이지가 하나의 서버에서 만들어졌기 때문에 해당 서버의 자원을 해당 도메인에서만 요청함
	 * 보안상 웹 브라우저는 다른 도메인에서 서버의 자원을 요청할 경우 막아 놓음
	 * 점점 자원과 웹 페이지를 분리하는 경우, 다른 서버의 자원을 요청하는 경우가 많아지면서 웹 브라우저는 외부 도메인과 통신하기 위한
	 * 표준인 CORS를 만듦
	 * 기본적으로 서버에서 클라이언트를 대상으로 리소스의 허용 여부를 결정함.
	 * */
	@Bean
	CorsConfigurationSource corsConfigurationSource(){
		CorsConfiguration configuration = new CorsConfiguration();
		// 로컬 React에서 오는 요청은 CORS 허용해준다.
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
		configuration.setAllowedMethods(Arrays.asList("GET", "PUT", "POST", "DELETE"));
		configuration.setAllowedHeaders(Arrays.asList("Access-Control-Allow-Origin", "Content-Type", "Access-Control-Allow-Headers", "Authorization", "X-Requested-With"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

}






