package crolopez.thecrmservice.user.domain.factories;

import crolopez.thecrmservice.shared.domain.entities.dto.UserDto;
import crolopez.thecrmservice.user.domain.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserResponseFactoryImpl implements UserResponseFactory {
    @Override
    public List<UserDto> create(List<UserEntity> userEntities) {
        return userEntities.stream().map(x -> {
            var user = new UserDto();
            user.setId(x.getId());
            user.setName(x.getName());
            user.setRole(x.getRole());
            return user;
        }).toList();
    }

    @Override
    public UserDto create(UserEntity userEntity) {
        var user = new UserDto();
        user.setId(userEntity.getId());
        user.setName(userEntity.getName());
        user.setRole(userEntity.getRole());
        return user;
    }
}
