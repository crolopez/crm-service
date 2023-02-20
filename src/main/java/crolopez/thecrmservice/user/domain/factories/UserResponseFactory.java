package crolopez.thecrmservice.user.domain.factories;

import crolopez.thecrmservice.shared.domain.dtos.UserDto;
import crolopez.thecrmservice.user.domain.entities.UserEntity;

import java.util.List;

public interface UserResponseFactory {
    List<UserDto> create(List<UserEntity> userEntities);
}
