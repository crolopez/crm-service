package unit.crolopez.thecrmservice.shared.infrastructure.auth;

import crolopez.thecrmservice.shared.domain.entities.dto.UserDto;
import crolopez.thecrmservice.shared.infrastructure.auth.AuthenticatedUserCache;
import crolopez.thecrmservice.shared.infrastructure.auth.AuthenticatedUserCacheImpl;
import crolopez.thecrmservice.shared.infrastructure.auth.OpaqueTokenIntrospectorImpl;
import crolopez.thecrmservice.shared.infrastructure.entities.AuthenticatedUserDataEntity;
import crolopez.thecrmservice.user.application.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

import static crolopez.thecrmservice.shared.domain.entities.dto.UserDto.RoleEnum.ADMIN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class OpaqueTokenIntrospectorImplUnitTest {

    @Mock
    AuthenticatedUserCache cache;

    @InjectMocks
    OpaqueTokenIntrospectorImpl opaqueTokenIntrospector;

    @AfterEach
    public void after() {
        Mockito.verifyNoMoreInteractions(cache);
        Mockito.reset(cache);
    }

    @Test
    public void whenCallIntrospect_thenTheExpectedResponseIsReturned() {
        UserDto userDto = new UserDto();
        userDto.setRole(ADMIN);
        when(cache.getAuthenticatedUser("fakeToken")).thenReturn(userDto);

        OAuth2AuthenticatedPrincipal response = opaqueTokenIntrospector.introspect("fakeToken");

        assertEquals(1, response.getAuthorities().stream().count());
        assertEquals("ROLE_ADMIN", response.getAuthorities().stream().toList().get(0).getAuthority());
        assertEquals(ADMIN, response.getAttribute("userId"));
        verify(cache).getAuthenticatedUser("fakeToken");
    }

}
