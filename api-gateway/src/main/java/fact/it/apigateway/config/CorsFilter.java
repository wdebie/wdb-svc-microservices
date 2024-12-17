package fact.it.apigateway.config;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
@Component
public class CorsFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange ctx, WebFilterChain chain) {
        ctx.getResponse().getHeaders().add("Access-Control-Allow-Origin", "*");
        ctx.getResponse().getHeaders().add("Access-Control-Allow-Methods", "GET, PUT, POST, DELETE, OPTIONS");
        ctx.getResponse().getHeaders().add("Access-Control-Allow-Headers", "*");
        
        if (ctx.getRequest().getMethod() == HttpMethod.OPTIONS) {
            ctx.getResponse().getHeaders().add("Access-Control-Max-Age", "1728000");
            ctx.getResponse().setStatusCode(HttpStatus.NO_CONTENT);
            return Mono.empty();
        } else {
            ctx.getResponse().getHeaders().add("Access-Control-Expose-Headers", "*");
            return chain.filter(ctx);
        }
    }
}