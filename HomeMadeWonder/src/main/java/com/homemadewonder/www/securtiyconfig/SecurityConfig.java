package com.homemadewonder.www.securtiyconfig;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

//	@Bean
//	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
//
//		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//				.cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
//					@Override
//					public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
//						CorsConfiguration config = new CorsConfiguration();
//						config.setAllowedOrigins(Collections.singletonList("http://localhost:5173"));
//						config.setAllowedMethods(Collections.singletonList("*"));
//						config.setAllowCredentials(true);
//						config.setAllowedHeaders(Collections.singletonList("*"));
//						config.setExposedHeaders(Arrays.asList("Authorization"));
//						config.setMaxAge(3600L);
//						return config;
//					}
//				})).csrf(withDefaults())
////				.addFilterAfter(new JWTTokenGenrateFilter(), BasicAuthenticationFilter.class)
////				.addFilterBefore(new JWTValidateFilter(), BasicAuthenticationFilter.class)
//				.authorizeHttpRequests((requests) -> requests.requestMatchers("/api/***").permitAll()
//						.anyRequest().permitAll())
//				.formLogin(Customizer.withDefaults())
//				.httpBasic(Customizer.withDefaults());
//		return http.build();
//	}

}
