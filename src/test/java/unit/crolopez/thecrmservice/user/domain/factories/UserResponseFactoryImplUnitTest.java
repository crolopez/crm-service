package unit.crolopez.thecrmservice.user.domain.factories;

import crolopez.thecrmservice.shared.domain.entities.dto.UserDto;
import crolopez.thecrmservice.user.domain.entities.UserEntity;
import crolopez.thecrmservice.user.domain.factories.UserResponseFactoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static crolopez.thecrmservice.shared.domain.entities.dto.UserDto.RoleEnum.ADMIN;
import static crolopez.thecrmservice.shared.domain.entities.dto.UserDto.RoleEnum.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserResponseFactoryImplUnitTest {
    @InjectMocks
    private UserResponseFactoryImpl factory;

    @Test
    public void givenValidEntity_whenCallCreate_thenTheExpectedResponseIsReturned() {
        UserEntity entity = UserEntity.builder()
                .id("fakeId")
                .name("fakeName")
                .role(ADMIN)
                .build();

        UserDto response = factory.create(entity);

        assertEquals("fakeId", response.getId());
        assertEquals("fakeName", response.getName());
        assertEquals(ADMIN, response.getRole());
    }

    @Test
    public void givenValidListOfEntities_whenCallCreate_thenTheExpectedResponseIsReturned() {
        List<UserEntity> entities = List.of(UserEntity.builder()
                .id("fakeId")
                .name("fakeName")
                .role(USER)
                .build());

        List<UserDto> response = factory.create(entities);

        assertEquals("fakeId", response.get(0).getId());
        assertEquals("fakeName", response.get(0).getName());
        assertEquals(USER, response.get(0).getRole());
    }
}
