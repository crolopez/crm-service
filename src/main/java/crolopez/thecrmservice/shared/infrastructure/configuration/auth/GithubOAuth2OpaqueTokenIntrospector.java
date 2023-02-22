package crolopez.thecrmservice.shared.infrastructure.configuration.auth;

import com.jayway.jsonpath.JsonPath;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.*;

@Component
public class GithubOAuth2OpaqueTokenIntrospector implements OpaqueTokenIntrospector {
    private final String TOKEN_TYPE_HINT = "token_type_hint";

    private final String ACCESS_TOKEN_TYPE = "access_token";

    private final String userInfoUri = "https://api.github.com/user";

    private final WebClient webClient = WebClient.create();

    @Override
    public OAuth2AuthenticatedPrincipal introspect(String token) {
        Map<String, String> params = new HashMap<>();
        params.put(TOKEN_TYPE_HINT, ACCESS_TOKEN_TYPE);

        // Duplicated request = inefficient way. To improve
        String body = getRequest(token).exchangeToMono(x -> x.bodyToMono(String.class)).block();
        String scope = getRequest(token).exchangeToMono(x -> Mono.just(x))
                .block().headers().header("X-OAuth-Scopes").get(0);;

        String username = JsonPath.read(body, "$.login");
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + scope.toUpperCase()));

        DefaultOAuth2AuthenticatedPrincipal principal = new DefaultOAuth2AuthenticatedPrincipal(
                "username",
                Collections.singletonMap("username", username),
                authorities
        );

        return principal;
    }

    private WebClient.RequestBodySpec getRequest(String token) {
        return webClient.post()
                .uri(userInfoUri)
                .headers(headers -> headers.setBearerAuth(token));
    }
}