package crolopez.thecrmservice.login.infrastructure.repositories;

import crolopez.thecrmservice.login.domain.entities.AuthenticatedUserEntity;
import crolopez.thecrmservice.login.infrastructure.entities.AuthenticatedUserDbEntity;
import crolopez.thecrmservice.shared.infrastructure.persistence.mappers.Mapper;
import crolopez.thecrmservice.shared.infrastructure.persistence.unit.UnitOfWork;
import crolopez.thecrmservice.shared.infrastructure.repositories.PersistenceRepositoryImpl;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedUserRepository extends PersistenceRepositoryImpl<AuthenticatedUserDbEntity, AuthenticatedUserEntity> {

    public AuthenticatedUserRepository(UnitOfWork unitOfWork, Mapper<AuthenticatedUserDbEntity, AuthenticatedUserEntity> mapper) {
        super(unitOfWork, mapper, AuthenticatedUserDbEntity.class);
    }
}