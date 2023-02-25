package crolopez.thecrmservice.shared.infrastructure.configuration.auth;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import crolopez.thecrmservice.login.application.services.LoginService;
import crolopez.thecrmservice.login.domain.entities.AuthenticatedUserEntity;
import crolopez.thecrmservice.shared.infrastructure.entities.AuthenticatedUserDataEntity;
import crolopez.thecrmservice.shared.infrastructure.repositories.OAuth2Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedUserCacheImpl implements AuthenticatedUserCache {

    private final int MAXIMUM_SIZE = 1000;
    private final Cache<String, AuthenticatedUserEntity> cache = Caffeine.newBuilder().maximumSize(MAXIMUM_SIZE).build();

    @Autowired
    private LoginService loginService;

    @Autowired
    private OAuth2Repository oAuth2Repository;

    @Override
    public AuthenticatedUserEntity getAuthenticatedUser(String token) {
        return cache.get(token, k -> createAuthenticatedUser(k));
    }

    private AuthenticatedUserEntity createAuthenticatedUser(String token) {
            AuthenticatedUserDataEntity userData = oAuth2Repository.getAuthenticatedUserData(token);
            return loginService.getUser(userData.getId());
    }
}
