package crolopez.thecrmservice.shared.infrastructure.auth;

import crolopez.thecrmservice.shared.domain.entities.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class OpaqueTokenIntrospectorImpl implements OpaqueTokenIntrospector {

    @Autowired
    AuthenticatedUserCache cache;

    @Override
    public OAuth2AuthenticatedPrincipal introspect(String token) {
        UserDto user = cache.getAuthenticatedUser(token);

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));

        DefaultOAuth2AuthenticatedPrincipal principal = new DefaultOAuth2AuthenticatedPrincipal(
                "userId",
                Collections.singletonMap("userId", user.getRole()),
                authorities
        );

        return principal;
    }

}