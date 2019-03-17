package hu.indicium.dev.lit.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

@Configuration
@EnableResourceServer
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private final OAuth2Properties oAuth2Properties;

    @Autowired
    public OAuth2ResourceServerConfig(OAuth2Properties oAuth2Properties) {
        this.oAuth2Properties = oAuth2Properties;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authorizeRequests().anyRequest().permitAll();
    }

    @Bean
    @Primary
    public RemoteTokenServices tokenServices() {
        final RemoteTokenServices tokenService = new RemoteTokenServices();
        tokenService.setCheckTokenEndpointUrl(oAuth2Properties.getCheckTokenUrl());
        tokenService.setClientId(oAuth2Properties.getClientId());
        tokenService.setClientSecret(oAuth2Properties.getClientSecret());
        return tokenService;
    }
}
