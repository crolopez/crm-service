package unit.crolopez.thecrmservice.user.domain.factories;

import crolopez.thecrmservice.user.domain.entities.UserEntity;
import crolopez.thecrmservice.user.domain.factories.UserEntityFactoryImpl;
import crolopez.thecrmservice.shared.domain.entities.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static crolopez.thecrmservice.shared.domain.entities.dto.UserDto.RoleEnum.ADMIN;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserEntityFactoryImplUnitTest {
    @InjectMocks
    private UserEntityFactoryImpl factory;

    @Test
    public void givenValidDto_whenCallCreate_thenTheExpectedResponseIsReturned() {
        UserDto userDto = new UserDto();
        userDto.setId("fakeId");
        userDto.setName("fakeName");
        userDto.setRole(ADMIN);

        UserEntity response = factory.create(userDto);

        assertEquals("fakeId", response.getId());
        assertEquals("fakeName", response.getName());
        assertEquals(ADMIN, response.getRole());
    }

}
