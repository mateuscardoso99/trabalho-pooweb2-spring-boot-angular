package com.trabalho.api.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.trabalho.api.model.Permissoes;

import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig{
    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider getDaoAuthProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsServiceImpl);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.cors(v -> v.configurationSource(corsConfigurationSource()))
            .csrf(CsrfConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests((request) -> {
                    request.requestMatchers("/login").permitAll()
                            .requestMatchers("/register").permitAll()
                            .requestMatchers("/public/**").permitAll()
                            .requestMatchers("/adm/**").hasAuthority(Permissoes.ADMIN_SISTEMA.name())
                            .requestMatchers("/adm-sistema-e-adm-empresa/**").hasAnyAuthority(Permissoes.ADMIN_SISTEMA.name(),Permissoes.ADMIN_EMPRESA.name())
                            .requestMatchers("/empresa/**").hasAuthority(Permissoes.ADMIN_EMPRESA.name())
                            .requestMatchers("/estabelecimento/**").hasAuthority(Permissoes.ADMIN_ESTABELECIMENTO.name())
                            .requestMatchers("/cliente/**").hasAuthority(Permissoes.CLIENTE.name()) //pra hasRole funcionar teria que ter prefixo 'ROLE_' na frente, mas ambos tem o mesmo efeito
                            .anyRequest().authenticated();
                }
            )
            .addFilterBefore(authenticationJwtTokenFilter(),UsernamePasswordAuthenticationFilter.class)
            .authenticationProvider(getDaoAuthProvider());
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("http://localhost:4200");
        configuration.addAllowedMethod(CorsConfiguration.ALL);
        configuration.setAllowedHeaders(List.of(
                "X-Requested-With", "Content-Type",
                "Authorization", "Origin", "Accept",
                "Access-Control-Request-Method", "Access-Control-Request-Headers"));
        configuration.setMaxAge(3600L);
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
