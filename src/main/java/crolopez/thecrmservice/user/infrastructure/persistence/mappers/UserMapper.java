package crolopez.thecrmservice.user.infrastructure.persistence.mappers;

import crolopez.thecrmservice.shared.domain.entities.dto.UserDto.RoleEnum;
import crolopez.thecrmservice.shared.infrastructure.persistence.mappers.Mapper;
import crolopez.thecrmservice.user.infrastructure.entities.UserDbEntity;
import crolopez.thecrmservice.user.domain.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements Mapper<UserDbEntity, UserEntity> {

    @Override
    public UserEntity externalEntityToEntity(UserDbEntity userDbEntity) {
        return UserEntity.builder()
                .id(userDbEntity.getId())
                .role(RoleEnum.valueOf(userDbEntity.getRole()))
                .name(userDbEntity.getName())
                .build();
    }

    @Override
    public UserDbEntity entityToExternalEntity(UserEntity userEntity) {
        return UserDbEntity.builder()
                .id(userEntity.getId())
                .role(userEntity.getRole().toString())
                .name(userEntity.getName())
                .build();
    }
}
