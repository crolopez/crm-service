package crolopez.thecrmservice.user.domain.factories;

import crolopez.thecrmservice.shared.domain.entities.dto.UserDto;
import crolopez.thecrmservice.user.domain.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserEntityFactoryImpl implements UserEntityFactory{
    @Override
    public UserEntity create(UserDto userDto) {
        return UserEntity.builder()
                .id(userDto.getId())
                .role(userDto.getRole())
                .build();
    }
}
