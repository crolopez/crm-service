package crolopez.thecrmservice.shared.infrastructure.configuration.auth;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import crolopez.thecrmservice.login.application.services.LoginService;
import crolopez.thecrmservice.shared.domain.entities.dto.UserDto;
import crolopez.thecrmservice.shared.infrastructure.entities.AuthenticatedUserDataEntity;
import crolopez.thecrmservice.shared.infrastructure.repositories.OAuth2Repository;
import crolopez.thecrmservice.user.application.services.UserService;
import crolopez.thecrmservice.user.domain.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class AuthenticatedUserCacheImpl implements AuthenticatedUserCache {

    private final int MAXIMUM_SIZE = 1000;
    private final Cache<String, UserDto> cache;
    @Autowired
    private UserService userService;

    @Autowired
    private OAuth2Repository oAuth2Repository;

    public AuthenticatedUserCacheImpl(@Value("${cache-expiration-time}") long cacheExpirationTime) {
        this.cache = Caffeine.newBuilder()
                .maximumSize(MAXIMUM_SIZE)
                .expireAfterWrite(cacheExpirationTime, TimeUnit.MINUTES)
                .build();
    }

    @Override
    public UserDto getAuthenticatedUser(String token) {
        return cache.get(token, k -> createAuthenticatedUser(k));
    }

    private UserDto createAuthenticatedUser(String token) {
            AuthenticatedUserDataEntity userData = oAuth2Repository.getAuthenticatedUserData(token);
            return userService.getUser(userData.getId());
    }
}
