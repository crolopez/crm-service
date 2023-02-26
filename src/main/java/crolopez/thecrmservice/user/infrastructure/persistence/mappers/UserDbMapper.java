package crolopez.thecrmservice.user.infrastructure.persistence.mappers;

import crolopez.thecrmservice.shared.domain.entities.dto.UserDto.RoleEnum;
import crolopez.thecrmservice.shared.infrastructure.persistence.mappers.DbMapper;
import crolopez.thecrmservice.shared.infrastructure.persistence.mappers.Mapper;
import crolopez.thecrmservice.user.infrastructure.entities.UserDbEntity;
import crolopez.thecrmservice.user.domain.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserDbMapper extends DbMapper<UserDbEntity, UserEntity> {

    @Override
    public UserEntity externalEntityToEntity(UserDbEntity userDbEntity) {
        return UserEntity.builder()
                .id(getUnescapedValue(userDbEntity.getId()))
                .role(RoleEnum.valueOf(userDbEntity.getRole()))
                .name(getUnescapedValue(userDbEntity.getName()))
                .build();
    }

    @Override
    public UserDbEntity entityToExternalEntity(UserEntity userEntity) {
        return UserDbEntity.builder()
                .id(getEscapedValue(userEntity.getId()))
                .role(userEntity.getRole().toString())
                .name(getEscapedValue(userEntity.getName()))
                .build();
    }
}
