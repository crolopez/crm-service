package crolopez.thecrmservice.shared.infrastructure.configuration.auth;

import crolopez.thecrmservice.login.domain.entities.AuthenticatedUserEntity;

public interface AuthenticatedUserCache {
    AuthenticatedUserEntity getAuthenticatedUser(String token);
}
