package crolopez.thecrmservice.user.infrastructure.repositories;

import crolopez.thecrmservice.shared.infrastructure.persistence.mappers.Mapper;
import crolopez.thecrmservice.user.infrastructure.entities.UserDbEntity;
import crolopez.thecrmservice.shared.infrastructure.repositories.PersistenceRepositoryImpl;
import crolopez.thecrmservice.shared.infrastructure.persistence.unit.UnitOfWork;
import crolopez.thecrmservice.user.domain.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserRepository extends PersistenceRepositoryImpl<UserDbEntity, UserEntity> {
    public UserRepository(UnitOfWork unitOfWork, Mapper<UserDbEntity, UserEntity> mapper) {
        super(unitOfWork, mapper, UserDbEntity.class);
    }
}
