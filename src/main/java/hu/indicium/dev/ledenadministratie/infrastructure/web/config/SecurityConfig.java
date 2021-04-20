package hu.indicium.dev.ledenadministratie.infrastructure.web.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final ObjectMapper objectMapper;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/studytypes").permitAll()
                .antMatchers(HttpMethod.GET, "/v3/api-docs/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(new OpenIDConnectJwtConverter())
                .and()
                .accessDeniedHandler(new CustomAccessDeniedHandler(objectMapper))
                .authenticationEntryPoint(new CustomAuthenticationEntrypoint(objectMapper));
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers()
                .antMatchers(HttpMethod.POST, "/api/v1/mail/verify")
                .antMatchers(HttpMethod.POST, "/api/v1/registrations");
    }

//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        final CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOriginPatterns(Collections.singletonList("*"));
//        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
//        configuration.setAllowCredentials(true);
//        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
//        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
}
