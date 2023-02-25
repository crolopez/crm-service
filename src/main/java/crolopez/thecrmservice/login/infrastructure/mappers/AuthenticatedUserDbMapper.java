package crolopez.thecrmservice.login.infrastructure.mappers;

import crolopez.thecrmservice.login.domain.entities.AuthenticatedUserEntity;
import crolopez.thecrmservice.login.infrastructure.entities.AuthenticatedUserDbEntity;
import crolopez.thecrmservice.shared.infrastructure.entities.valueobjects.Role;
import crolopez.thecrmservice.shared.infrastructure.persistence.mappers.Mapper;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedUserDbMapper implements Mapper<AuthenticatedUserDbEntity, AuthenticatedUserEntity> {

    @Override
    public AuthenticatedUserEntity externalEntityToEntity(AuthenticatedUserDbEntity authenticatedUserDbEntity) {
        return AuthenticatedUserEntity.builder()
                .userId(authenticatedUserDbEntity.getId())
                .role(Role.valueOf(authenticatedUserDbEntity.getRole()))
                .build();
    }

    @Override
    public AuthenticatedUserDbEntity entityToExternalEntity(AuthenticatedUserEntity authenticatedUserEntity) {
        return AuthenticatedUserDbEntity.builder()
                .id(authenticatedUserEntity.getUserId())
                .role(authenticatedUserEntity.getRole().toString())
                .build();
    }
}
