package crolopez.thecrmservice.user.persistence.mappers;

import crolopez.thecrmservice.shared.infrastructure.persistence.mappers.Mapper;
import crolopez.thecrmservice.user.infrastructure.persistence.models.UserDbEntity;
import crolopez.thecrmservice.user.domain.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements Mapper<UserDbEntity, UserEntity> {

    @Override
    public UserEntity dbEntityToEntity(UserDbEntity userDbEntity) {
        return UserEntity.builder()
                .id(userDbEntity.getId())
                .isAdmin(userDbEntity.getIsAdmin())
                .build();
    }

    @Override
    public UserDbEntity entityToDbEntity(UserEntity userEntity) {
        return UserDbEntity.builder()
                .id(userEntity.getId())
                .isAdmin(userEntity.getIsAdmin())
                .build();
    }
}
