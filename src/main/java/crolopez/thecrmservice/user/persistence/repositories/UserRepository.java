package crolopez.thecrmservice.user.persistence.repositories;

import crolopez.thecrmservice.shared.infrastructure.persistence.mappers.Mapper;
import crolopez.thecrmservice.user.infrastructure.persistence.models.UserDbEntity;
import crolopez.thecrmservice.shared.infrastructure.persistence.repositories.RepositoryImpl;
import crolopez.thecrmservice.shared.infrastructure.persistence.repositories.unit.UnitOfWork;
import crolopez.thecrmservice.user.domain.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserRepository extends RepositoryImpl<UserDbEntity, UserEntity> {
    public UserRepository(UnitOfWork unitOfWork, Mapper<UserDbEntity, UserEntity> mapper) {
        super(unitOfWork, mapper, UserDbEntity.class);
    }
}
