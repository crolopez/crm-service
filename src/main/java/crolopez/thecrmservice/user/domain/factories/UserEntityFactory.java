package crolopez.thecrmservice.user.domain.factories;

import crolopez.thecrmservice.shared.entities.dto.UserDto;
import crolopez.thecrmservice.user.domain.entities.UserEntity;

public interface UserEntityFactory {
    UserEntity create(UserDto userDto);
}
