package unit.crolopez.thecrmservice.login.application.services;

import crolopez.thecrmservice.login.application.services.GithubLoginServiceImpl;
import crolopez.thecrmservice.shared.domain.entities.dto.UserDto;
import crolopez.thecrmservice.shared.infrastructure.entities.AccessTokenDataEntity;
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

import javax.persistence.EntityNotFoundException;

import static crolopez.thecrmservice.shared.domain.entities.dto.UserDto.RoleEnum.ADMIN;
import static crolopez.thecrmservice.shared.domain.entities.dto.UserDto.RoleEnum.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GithubLoginServiceImplUnitTest {

    private final String expectedRedirectUrl = "https://github.com/login/oauth/authorize?client_id=fakeClientId&scope=user&state=";

    @InjectMocks
    private GithubLoginServiceImpl githubLoginService = new GithubLoginServiceImpl("fakeClientId", true);

    @Mock
    private OAuth2Repository oAuth2Repository;

    @Mock
    private UserService userService;

    @AfterEach
    public void after() {
        Mockito.verifyNoMoreInteractions(oAuth2Repository, userService);
        Mockito.reset(oAuth2Repository, userService);
    }

    @Test
    public void whenCallGetLoginUrl_thenTheExpectedResponseIsReturned() {
        String response = new GithubLoginServiceImpl("fakeClientId", true).getLoginUrl();

        assertTrue(response.startsWith(expectedRedirectUrl));
    }

    @Test
    public void givenAnUserWithValidScope_whenCallGetAccessToken_thenTheExpectedResponseIsReturned() {
        var accessTokenDataEntity = AccessTokenDataEntity.builder()
                .accessToken("fakeToken")
                .scope(ADMIN)
                .build();
        var authenticatedUserDataEntity = AuthenticatedUserDataEntity.builder()
                .id("fakeId")
                .name("fakeName")
                .build();
        var userDto = new UserDto();
        userDto.setRole(ADMIN);
        when(oAuth2Repository.getAccessToken("fakeCode", "fakeState")).thenReturn(accessTokenDataEntity);
        when(oAuth2Repository.getAuthenticatedUserData("fakeToken")).thenReturn(authenticatedUserDataEntity);
        when(userService.getUser("fakeId")).thenReturn(userDto);

        String response = githubLoginService.getAccessToken("fakeCode", "fakeState");

        assertEquals("fakeToken", response);
        verify(oAuth2Repository).getAccessToken("fakeCode", "fakeState");
        verify(oAuth2Repository).getAuthenticatedUserData("fakeToken");
        verify(userService).getUser("fakeId");
    }

    @Test
    public void givenAnUserWithInvalidScope_whenCallGetAccessToken_thenTheExpectedResponseIsReturned() {
        var accessTokenDataEntity = AccessTokenDataEntity.builder()
                .accessToken("fakeToken")
                .scope(ADMIN)
                .build();
        var authenticatedUserDataEntity = AuthenticatedUserDataEntity.builder()
                .id("fakeId")
                .name("fakeName")
                .build();
        var userDto = new UserDto();
        userDto.setRole(USER);
        when(oAuth2Repository.getAccessToken("fakeCode", "fakeState")).thenReturn(accessTokenDataEntity);
        when(oAuth2Repository.getAuthenticatedUserData("fakeToken")).thenReturn(authenticatedUserDataEntity);
        when(userService.getUser("fakeId")).thenReturn(userDto);

        String response = githubLoginService.getAccessToken("fakeCode", "fakeState");

        assertEquals(null, response);
        verify(oAuth2Repository).getAccessToken("fakeCode", "fakeState");
        verify(oAuth2Repository).getAuthenticatedUserData("fakeToken");
        verify(userService).getUser("fakeId");
    }

    @Test
    public void givenANonExistingUser_whenCallGetAccessToken_thenTheExpectedResponseIsReturned() {
        var accessTokenDataEntity = AccessTokenDataEntity.builder()
                .accessToken("fakeToken")
                .scope(ADMIN)
                .build();
        var authenticatedUserDataEntity = AuthenticatedUserDataEntity.builder()
                .id("fakeId")
                .name("fakeName")
                .build();
        var userDto = new UserDto();
        userDto.setId("fakeId");
        userDto.setName("fakeName");
        userDto.setRole(ADMIN);
        when(oAuth2Repository.getAccessToken("fakeCode", "fakeState")).thenReturn(accessTokenDataEntity);
        when(oAuth2Repository.getAuthenticatedUserData("fakeToken")).thenReturn(authenticatedUserDataEntity);
        when(userService.getUser("fakeId")).thenThrow(new EntityNotFoundException());

        String response = githubLoginService.getAccessToken("fakeCode", "fakeState");

        assertEquals("fakeToken", response);
        verify(oAuth2Repository).getAccessToken("fakeCode", "fakeState");
        verify(oAuth2Repository).getAuthenticatedUserData("fakeToken");
        verify(userService).getUser("fakeId");
        verify(userService).createUser(userDto);
        verify(userService).countUsers();
    }

}
