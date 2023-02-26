package unit.crolopez.thecrmservice.user.infrastructure.persistence.mappers;

import crolopez.thecrmservice.user.domain.entities.UserEntity;
import crolopez.thecrmservice.user.infrastructure.entities.UserDbEntity;
import crolopez.thecrmservice.user.infrastructure.persistence.mappers.UserDbMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static crolopez.thecrmservice.shared.domain.entities.dto.UserDto.RoleEnum.ADMIN;
import static crolopez.thecrmservice.shared.domain.entities.dto.UserDto.RoleEnum.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserDbMapperUnitTest {

    @InjectMocks
    private UserDbMapper mapper;

    @Test
    public void givenValidEntity_whenCallEntityToExternalEntity_thenTheExpectedResponseIsReturned() {
        UserEntity entity = UserEntity.builder()
                .id("fakeId")
                .name("fakeName")
                .role(USER)
                .build();

        UserDbEntity response = mapper.entityToExternalEntity(entity);

        assertEquals("fakeId", response.getId());
        assertEquals("fakeName", response.getName());
        assertEquals("USER", response.getRole());
    }

    @Test
    public void givenValidDbEntity_whenCallExternalEntityToEntity_thenTheExpectedResponseIsReturned() {
        UserDbEntity userDbEntity = UserDbEntity.builder()
            .id("fakeId")
            .name("fakeName")
            .role("ADMIN").build();

        UserEntity response = mapper.externalEntityToEntity(userDbEntity);

        assertEquals("fakeId", response.getId());
        assertEquals("fakeName", response.getName());
        assertEquals(ADMIN, response.getRole());
    }

}
