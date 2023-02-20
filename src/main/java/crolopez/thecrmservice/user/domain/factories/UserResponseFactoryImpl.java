package crolopez.thecrmservice.user.domain.factories;

import crolopez.thecrmservice.shared.domain.dtos.UserDto;
import crolopez.thecrmservice.user.domain.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserResponseFactoryImpl implements UserResponseFactory {
    @Override
    public List<UserDto> create(List<UserEntity> userEntities) {
        return userEntities.stream().map(x -> {
            var user = new UserDto();
            user.setId(x.getId().toString());
            user.setIsAdmin(x.getIsAdmin());
            return user;
        }).toList();
    }

    @Override
    public UserDto create(UserEntity userEntity) {
        var user = new UserDto();
        user.setId(userEntity.getId());
        user.setIsAdmin(userEntity.getIsAdmin());
        return user;
    }
}
