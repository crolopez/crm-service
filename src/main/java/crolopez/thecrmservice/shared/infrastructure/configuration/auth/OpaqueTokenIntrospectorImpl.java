package crolopez.thecrmservice.shared.infrastructure.configuration.auth;

import crolopez.thecrmservice.shared.infrastructure.entities.AuthenticatedUserDataEntity;
import crolopez.thecrmservice.shared.infrastructure.repositories.OAuth2Repository;
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
    OAuth2Repository oAuth2Repository;

    @Override
    public OAuth2AuthenticatedPrincipal introspect(String token) {
        AuthenticatedUserDataEntity userData = oAuth2Repository.getAuthenticatedUserData(token);

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + userData.getScope()));

        DefaultOAuth2AuthenticatedPrincipal principal = new DefaultOAuth2AuthenticatedPrincipal(
                "username",
                Collections.singletonMap("username", userData.getUsername()),
                authorities
        );

        return principal;
    }

}