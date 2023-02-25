package crolopez.thecrmservice.user.domain.factories;

import crolopez.thecrmservice.shared.entities.UserDto;
import crolopez.thecrmservice.user.domain.entities.UserEntity;

import java.util.List;

public interface UserResponseFactory {
    List<UserDto> create(List<UserEntity> userEntities);

    UserDto create(UserEntity userEntity);
}
