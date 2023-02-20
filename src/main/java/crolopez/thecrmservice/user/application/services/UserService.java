package crolopez.thecrmservice.user.application.services;

import crolopez.thecrmservice.shared.domain.dtos.CustomerDto;
import crolopez.thecrmservice.shared.domain.dtos.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers();
}
