package com.dongmanee.domain.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.dongmanee.domain.security.entrypoint.CustomAuthenticationEntryPoint;
import com.dongmanee.domain.security.filter.ClubUserAuthenticationFilter;
import com.dongmanee.domain.security.filter.CustomAuthenticationFilter;
import com.dongmanee.domain.security.filter.JwtAuthenticationFilter;
import com.dongmanee.domain.security.filter.JwtExceptionFilter;
import com.dongmanee.domain.security.handler.CustomAccessDeniedHandler;
import com.dongmanee.domain.security.handler.CustomAuthenticationSuccessHandler;
import com.dongmanee.domain.security.provider.JwtProvider;
import com.dongmanee.domain.security.service.AuthService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig implements WebMvcConfigurer {

	private final JwtProvider jwtProvider;
	private final AuthService authService;
	private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
	private final ClubUserAuthenticationFilter clubUserAuthenticationFilter;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		AuthenticationManager authenticationManager = getAuthenticationManager(http);

		http.httpBasic(AbstractHttpConfigurer::disable) // spring security 기본 인증 해제
			.formLogin(AbstractHttpConfigurer::disable)
			.csrf(AbstractHttpConfigurer::disable) // CSRF 공격 방지 기능 해제
			.cors(Customizer.withDefaults()) // CORS 설정
			.headers((headerConfig) ->    // h2-console 표시를 위해
				headerConfig.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
			.sessionManagement(
				(session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // session 비활성
			// HTTP 요청에 대한 인가 설정
			.authorizeHttpRequests(
				authorizedRequests -> authorizedRequests
					.requestMatchers(new AntPathRequestMatcher("/clubs/**", HttpMethod.GET.name()))
					.permitAll()
					.requestMatchers(new AntPathRequestMatcher("/login"))
					.permitAll() // 모든 요청에 대해서 인증 없이 허용
					.requestMatchers(new AntPathRequestMatcher("/oauth2/**"))
					.permitAll() // 모든 요청에 대해서 인증 없이 허용
					.requestMatchers(new AntPathRequestMatcher("/signup"))
					.permitAll()
					.anyRequest().permitAll()
			)
			.exceptionHandling((exception) -> {
				exception.accessDeniedHandler(new CustomAccessDeniedHandler());
				exception.authenticationEntryPoint(new CustomAuthenticationEntryPoint());
			})

			// 로컬 로그인
			.authenticationManager(authenticationManager)
			.addFilter(getCustomAuthenticationFilter(authenticationManager))
			// OAuth2 로그인
			.oauth2Login((oauth2Login) -> oauth2Login.userInfoEndpoint(
					(userInfoEndpointConfig -> userInfoEndpointConfig.userService(authService)))
				.successHandler(customAuthenticationSuccessHandler))

			// JWT 검증 및 인증
			.addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(new JwtExceptionFilter(), JwtAuthenticationFilter.class)
			.addFilterAfter(clubUserAuthenticationFilter, JwtAuthenticationFilter.class);

		return http.build();
	}

	private AuthenticationManager getAuthenticationManager(HttpSecurity http) throws Exception {
		// 인증 매니저를 구성하는 빌더 클래스
		AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(
			AuthenticationManagerBuilder.class);

		// userDetailService: 사용자 인증 정보를 검색할 때 사용하는 서비스 (userService)
		// passwordEncoder: 패스워드 인코딩을 위해 사용
		authenticationManagerBuilder.userDetailsService(authService).passwordEncoder(passwordEncoder());
		return authenticationManagerBuilder.build();
	}

	private CustomAuthenticationFilter getCustomAuthenticationFilter(AuthenticationManager authenticationManager) {
		CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager);
		// 로그인 성공 시
		customAuthenticationFilter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
		// 로그인 실패 시
		// customAuthenticationFilter.setAuthenticationFailureHandler(customAuthenticationSuccessHandler);
		return customAuthenticationFilter;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
