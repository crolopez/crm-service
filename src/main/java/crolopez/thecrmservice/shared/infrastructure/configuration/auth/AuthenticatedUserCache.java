package crolopez.thecrmservice.shared.infrastructure.configuration.auth;

import crolopez.thecrmservice.shared.domain.entities.dto.UserDto;
import crolopez.thecrmservice.user.domain.entities.UserEntity;

public interface AuthenticatedUserCache {
    UserDto getAuthenticatedUser(String token);
}
