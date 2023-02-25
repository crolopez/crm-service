package crolopez.thecrmservice.user.application.services;

import crolopez.thecrmservice.shared.entities.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers();

    UserDto createUser(UserDto userDto);

    UserDto getUser(String id);

    UserDto deleteUser(String id);

    UserDto updateUser(String id, UserDto userDto);
}
