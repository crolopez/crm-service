package unit.crolopez.thecrmservice.user.infrastructure.repositories;

import crolopez.thecrmservice.user.domain.entities.UserEntity;
import crolopez.thecrmservice.user.infrastructure.repositories.UserRepository;
import crolopez.thecrmservice.shared.infrastructure.persistence.mappers.Mapper;
import crolopez.thecrmservice.user.infrastructure.entities.UserDbEntity;
import crolopez.thecrmservice.shared.infrastructure.persistence.unit.UnitOfWork;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryUnitTest {
    @Mock
    Mapper mapper;

    @Mock
    UnitOfWork unitOfWork;

    @InjectMocks
    private UserRepository userRepository;

    @AfterEach
    public void after() {
        Mockito.verifyNoMoreInteractions(unitOfWork, mapper);
        Mockito.reset(unitOfWork, mapper);
    }

    @Test
    public void whenCallGet_thenTheExpectedResponseIsReturned() {
        UserDbEntity dbEntity = Mockito.mock(UserDbEntity.class);
        List<UserDbEntity> dbEntities = Arrays.asList(dbEntity);
        UserEntity userEntity = Mockito.mock(UserEntity.class);
        when(unitOfWork.get(UserDbEntity.class)).thenReturn(dbEntities);
        when(mapper.externalEntityToEntity(dbEntity)).thenReturn(userEntity);

        List<UserEntity> response = userRepository.get();

        assertEquals(1, response.stream().count());
        assertEquals(userEntity, response.get(0));
        Mockito.verify(unitOfWork).get(UserDbEntity.class);
        Mockito.verify(mapper).externalEntityToEntity(dbEntity);
    }

    @Test
    public void givenAValidId_whenCallGet_thenTheExpectedResponseIsReturned() {
        UserDbEntity dbEntity = Mockito.mock(UserDbEntity.class);
        List<UserDbEntity> dbEntities = Arrays.asList(dbEntity);
        UserEntity userEntity = Mockito.mock(UserEntity.class);
        when(unitOfWork.get("id", "validId", UserDbEntity.class)).thenReturn(dbEntities);
        when(mapper.externalEntityToEntity(dbEntity)).thenReturn(userEntity);

        UserEntity response = userRepository.get("validId");

        assertEquals(userEntity, response);
        Mockito.verify(unitOfWork).get("id", "validId", UserDbEntity.class);
        Mockito.verify(mapper).externalEntityToEntity(dbEntity);
    }

    @Test
    public void givenAnNonExistingId_whenCallGet_thenTheExpectedExceptionIsThrown() {
        UserDbEntity dbEntity = Mockito.mock(UserDbEntity.class);
        UserEntity userEntity = Mockito.mock(UserEntity.class);
        when(unitOfWork.get("id", "nonExistingId", UserDbEntity.class)).thenThrow(new ArrayIndexOutOfBoundsException());
        when(mapper.externalEntityToEntity(dbEntity)).thenReturn(userEntity);

        assertThrows(EntityNotFoundException.class, () -> userRepository.get("nonExistingId"));
    }

    @Test
    public void givenAValidId_whenCallDelete_thenTheExpectedResponseIsReturned() {
        userRepository.delete("validId");

        Mockito.verify(unitOfWork).delete("validId", UserDbEntity.class);
    }

    @Test
    public void whenCallCreate_thenTheExpectedResponseIsReturned() {
        UserDbEntity dbEntity = Mockito.mock(UserDbEntity.class);
        UserEntity userEntity = Mockito.mock(UserEntity.class);
        when(mapper.entityToExternalEntity(userEntity)).thenReturn(dbEntity);

        UserEntity response = userRepository.create(userEntity);

        assertEquals(userEntity, response);
        Mockito.verify(unitOfWork).create(dbEntity);
        Mockito.verify(mapper).entityToExternalEntity(userEntity);
    }

    @Test
    public void whenCallUpdate_thenTheExpectedResponseIsReturned() {
        UserDbEntity dbEntity = Mockito.mock(UserDbEntity.class);
        UserEntity userEntity = Mockito.mock(UserEntity.class);
        when(mapper.entityToExternalEntity(userEntity)).thenReturn(dbEntity);

        UserEntity response = userRepository.update("fakeId", userEntity);

        assertEquals(userEntity, response);
        Mockito.verify(unitOfWork).update("fakeId", dbEntity, UserDbEntity.class);
        Mockito.verify(mapper).entityToExternalEntity(userEntity);
    }

    @Test
    public void whenCallCount_thenTheExpectedResponseIsReturned() {
        when(unitOfWork.count(UserDbEntity.class)).thenReturn(49L);

        Long response = userRepository.count();

        assertEquals(49L, response);
        Mockito.verify(unitOfWork).count(UserDbEntity.class);
    }

}
