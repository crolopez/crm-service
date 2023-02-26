package unit.crolopez.thecrmservice.user.application.services;

import crolopez.thecrmservice.user.domain.entities.UserEntity;
import crolopez.thecrmservice.shared.domain.entities.dto.UserDto;
import crolopez.thecrmservice.user.domain.entities.UserEntity;
import crolopez.thecrmservice.shared.domain.entities.dto.UserDto;
import crolopez.thecrmservice.user.application.services.UserServiceImpl;
import crolopez.thecrmservice.user.domain.entities.UserEntity;
import crolopez.thecrmservice.user.domain.factories.UserEntityFactory;
import crolopez.thecrmservice.user.domain.factories.UserResponseFactory;
import crolopez.thecrmservice.user.infrastructure.repositories.UserRepository;
import crolopez.thecrmservice.shared.domain.entities.dto.UserDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplUnitTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserResponseFactory userResponseFactory;

    @Mock
    private UserEntityFactory userEntityFactory;

    @InjectMocks
    private UserServiceImpl userService;

    @AfterEach
    public void after() {
        Mockito.verifyNoMoreInteractions(userResponseFactory, userRepository, userEntityFactory);
        Mockito.reset(userResponseFactory, userRepository, userEntityFactory);
    }

    @Test
    public void whenCallGetUsers_thenTheExpectedResponseIsReturned() {
        var userEntity = Mockito.mock(UserEntity.class);
        List<UserEntity> userEntities = List.of(userEntity);
        UserDto userDto = Mockito.mock(UserDto.class);
        List<UserDto> userDtos = List.of(userDto);
        when(userRepository.get()).thenReturn(userEntities);
        when(userResponseFactory.create(userEntities)).thenReturn(userDtos);

        List<UserDto> response = userService.getUsers();

        assertEquals(userDtos, response);
        Mockito.verify(userRepository).get();
        Mockito.verify(userResponseFactory).create(userEntities);
    }

    @Test
    public void whenCallCreateUser_thenTheExpectedResponseIsReturned() {
        var userEntity = Mockito.mock(UserEntity.class);
        UserDto userDto = Mockito.mock(UserDto.class);
        UserDto userResponseDto = Mockito.mock(UserDto.class);
        when(userEntityFactory.create(userDto)).thenReturn(userEntity);
        when(userRepository.create(userEntity)).thenReturn(userEntity);
        when(userResponseFactory.create(userEntity)).thenReturn(userResponseDto);

        UserDto response = userService.createUser(userDto);

        assertEquals(userResponseDto, response);
        Mockito.verify(userRepository).create(userEntity);
        Mockito.verify(userEntityFactory).create(userDto);
        Mockito.verify(userResponseFactory).create(userEntity);
    }

    @Test
    public void whenCallGetUser_thenTheExpectedResponseIsReturned() {
        var userEntity = Mockito.mock(UserEntity.class);
        UserDto userResponseDto = Mockito.mock(UserDto.class);
        when(userRepository.get("fakeId")).thenReturn(userEntity);
        when(userResponseFactory.create(userEntity)).thenReturn(userResponseDto);

        UserDto response = userService.getUser("fakeId");

        assertEquals(userResponseDto, response);
        Mockito.verify(userRepository).get("fakeId");
        Mockito.verify(userResponseFactory).create(userEntity);
    }

    @Test
    public void whenCallDeleteUser_thenTheExpectedResponseIsReturned() {
        var userEntity = Mockito.mock(UserEntity.class);
        UserDto userResponseDto = Mockito.mock(UserDto.class);
        when(userRepository.get("fakeId")).thenReturn(userEntity);
        when(userResponseFactory.create(userEntity)).thenReturn(userResponseDto);

        UserDto response = userService.deleteUser("fakeId");

        assertEquals(userResponseDto, response);
        Mockito.verify(userRepository).delete("fakeId");
        Mockito.verify(userResponseFactory).create(userEntity);
    }

    @Test
    public void whenCallUpdateUser_thenTheExpectedResponseIsReturned() {
        var userEntity = Mockito.mock(UserEntity.class);
        UserDto userDto = Mockito.mock(UserDto.class);
        UserDto userResponseDto = Mockito.mock(UserDto.class);
        when(userRepository.get("fakeId")).thenReturn(userEntity);
        when(userRepository.update("fakeId", userEntity)).thenReturn(userEntity);
        when(userResponseFactory.create(userEntity)).thenReturn(userResponseDto);
        when(userEntityFactory.create(userDto)).thenReturn(userEntity);

        UserDto response = userService.updateUser("fakeId", userDto);

        assertEquals(userResponseDto, response);
        Mockito.verify(userRepository).update("fakeId", userEntity);
        Mockito.verify(userResponseFactory).create(userEntity);
    }

    @Test
    public void whenCallCountUsers_thenTheExpectedResponseIsReturned() {
        when(userRepository.count()).thenReturn(48L);

        Long response = userService.countUsers();

        assertEquals(48L, response);
    }

}
