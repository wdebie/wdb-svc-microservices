package fact.it.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {
        serverHttpSecurity
                .authorizeExchange(exchange ->
                        exchange.pathMatchers(HttpMethod.GET,"/schedules")
                                .permitAll()
                                .anyExchange()
                                .authenticated()
                )
                .authorizeExchange(exchange ->
                        exchange.pathMatchers(HttpMethod.GET,"/artists")
                                .permitAll()
                                .anyExchange()
                                .authenticated()
                )
                .authorizeExchange(exchange ->
                        exchange.pathMatchers(HttpMethod.GET,"/fooditems")
                                .permitAll()
                                .anyExchange()
                                .authenticated()
                )
                .authorizeExchange(exchange ->
                        exchange.pathMatchers(HttpMethod.GET,"/foodtrucks")
                                .permitAll()
                                .anyExchange()
                                .authenticated()
                )
                .authorizeExchange(exchange ->
                        exchange.pathMatchers(HttpMethod.GET,"/stages")
                                .permitAll()
                                .anyExchange()
                                .authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(withDefaults())
                );
        return serverHttpSecurity.build();
    }
}
