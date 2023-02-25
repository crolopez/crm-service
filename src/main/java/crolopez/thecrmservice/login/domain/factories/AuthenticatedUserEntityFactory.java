package crolopez.thecrmservice.login.domain.factories;

import crolopez.thecrmservice.login.domain.entities.AuthenticatedUserEntity;
import crolopez.thecrmservice.shared.infrastructure.entities.valueobjects.Role;

public interface AuthenticatedUserEntityFactory {
    AuthenticatedUserEntity create(String userId, Role role);
}
