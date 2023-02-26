package crolopez.thecrmservice.shared.infrastructure.auth;

import crolopez.thecrmservice.shared.domain.entities.dto.UserDto;

public interface AuthenticatedUserCache {
    UserDto getAuthenticatedUser(String token);
}
