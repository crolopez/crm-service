package crolopez.thecrmservice.user.application.services;

import crolopez.thecrmservice.shared.application.repositories.Repository;
import crolopez.thecrmservice.shared.domain.dtos.UserDto;
import crolopez.thecrmservice.user.domain.entities.UserEntity;
import crolopez.thecrmservice.user.domain.factories.UserEntityFactory;
import crolopez.thecrmservice.user.domain.factories.UserResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserServiceImpl implements UserService{

    @Autowired
    private Repository<UserEntity> userEntityRepository;

    @Autowired
    private UserResponseFactory userResponseFactory;

    @Autowired
    private UserEntityFactory userEntityFactory;

    @Override
    public List<UserDto> getUsers() {
        List<UserEntity> userEntities = userEntityRepository.get();
        return userResponseFactory.create(userEntities);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        UserEntity userEntity = userEntityFactory.create(userDto);
        userEntity = userEntityRepository.create(userEntity);
        return userResponseFactory.create(userEntity);
    }

    @Override
    public UserDto getUser(String id) {
        UserEntity userEntity = userEntityRepository.get(id);
        return userResponseFactory.create(userEntity);
    }

    @Override
    public UserDto deleteUser(String id) {
        UserEntity userEntity = userEntityRepository.get(id);
        userEntityRepository.delete(id);
        return userResponseFactory.create(userEntity);
    }

    @Override
    public UserDto updateUser(String id, UserDto userDto) {
        userEntityRepository.get(id);
        UserEntity userEntity = userEntityFactory.create(userDto);
        userEntity = userEntityRepository.update(id, userEntity);
        return userResponseFactory.create(userEntity);
    }
}
