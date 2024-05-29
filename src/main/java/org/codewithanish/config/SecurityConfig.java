package org.codewithanish.config;


import org.codewithanish.OAuthProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.authentication.JwtIssuerAuthenticationManagerResolver;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;
import java.util.stream.Collectors;

import static org.codewithanish.controller.ControllerConstant.END_POINT;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private OAuthProperties oAuthProperties;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.authorizeHttpRequests(r -> r
                .requestMatchers( END_POINT+"/sign-in",END_POINT+"/login").permitAll()
                .anyRequest().authenticated());
        httpSecurity.oauth2ResourceServer(oauth -> oauth.authenticationManagerResolver(authenticationManagerResolver()));
        httpSecurity.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity.cors(c -> c.configurationSource(corsConfigurationSource()));
        return httpSecurity.build();
    }

    @Bean
    public JwtIssuerAuthenticationManagerResolver authenticationManagerResolver() {
        return new JwtIssuerAuthenticationManagerResolver(oAuthProperties.getJwts().stream().collect
                (Collectors.toMap(OAuthProperties.Jwt::getIssuerUrl, j -> authenticationManager(j.getJwksUrl())))::get);
    }

    private AuthenticationManager authenticationManager(String jwkSetUri) {
        JwtDecoder decoder = NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
        JwtAuthenticationProvider provider = new JwtAuthenticationProvider(decoder);
        return new ProviderManager(provider);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource()
    {
        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.setExposedHeaders(List.of("Access-Control-Allow-Origin"));
        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**",corsConfiguration);
        return corsConfigurationSource;
    }

    @Bean
    public WebSecurityCustomizer ignoringCustomizer() {
        return (web) -> web.ignoring().requestMatchers(  "/h2-console/**");
    }

}
