package unit.crolopez.thecrmservice.shared.infrastructure.auth;

import crolopez.thecrmservice.login.application.services.GithubLoginServiceImpl;
import crolopez.thecrmservice.shared.domain.entities.dto.UserDto;
import crolopez.thecrmservice.shared.infrastructure.auth.AuthenticatedUserCacheImpl;
import crolopez.thecrmservice.shared.infrastructure.entities.AuthenticatedUserDataEntity;
import crolopez.thecrmservice.shared.infrastructure.repositories.OAuth2Repository;
import crolopez.thecrmservice.user.application.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticatedUserCacheImplUnitTest {

    @Mock
    OAuth2Repository oAuth2Repository;

    @Mock
    UserService userService;

    @InjectMocks
    AuthenticatedUserCacheImpl authenticatedUserCache = new AuthenticatedUserCacheImpl(2);

    @AfterEach
    public void after() {
        Mockito.verifyNoMoreInteractions(userService, oAuth2Repository);
        Mockito.reset(userService, oAuth2Repository);
    }

    @Test
    public void whenCallGetAuthenticatedUser_thenTheExpectedResponseIsReturned() {
        UserDto userDto = Mockito.mock(UserDto.class);
        AuthenticatedUserDataEntity userDataEntity = AuthenticatedUserDataEntity.builder()
            .id("fakeId")
            .build();
        when(oAuth2Repository.getAuthenticatedUserData("fakeToken")).thenReturn(userDataEntity);
        when(userService.getUser("fakeId")).thenReturn(userDto);

        UserDto response = authenticatedUserCache.getAuthenticatedUser("fakeToken");

        assertEquals(userDto, response);
        verify(userService).getUser("fakeId");
        verify(oAuth2Repository).getAuthenticatedUserData("fakeToken");
    }
}
