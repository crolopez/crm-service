package crolopez.thecrmservice.login.domain.factories;

import crolopez.thecrmservice.login.domain.entities.AuthenticatedUserEntity;
import crolopez.thecrmservice.shared.infrastructure.entities.valueobjects.Role;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedUserEntityFactoryImpl implements AuthenticatedUserEntityFactory {
    @Override
    public AuthenticatedUserEntity create(String userId, Role role) {
        return AuthenticatedUserEntity.builder()
                .userId(userId)
                .role(role)
                .build();
    }
}
