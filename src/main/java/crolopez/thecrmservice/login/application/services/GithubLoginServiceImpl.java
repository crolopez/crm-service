package crolopez.thecrmservice.login.application.services;

import crolopez.thecrmservice.shared.domain.entities.dto.UserDto;
import crolopez.thecrmservice.shared.infrastructure.entities.AccessTokenDataEntity;
import crolopez.thecrmservice.shared.infrastructure.entities.AuthenticatedUserDataEntity;
import crolopez.thecrmservice.shared.infrastructure.repositories.OAuth2Repository;
import crolopez.thecrmservice.user.application.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

import static crolopez.thecrmservice.shared.domain.entities.dto.UserDto.RoleEnum.ADMIN;
import static crolopez.thecrmservice.shared.domain.entities.dto.UserDto.RoleEnum.USER;
import static crolopez.thecrmservice.shared.domain.entities.dto.UserDto.RoleEnum;

@Component
public class GithubLoginServiceImpl implements LoginService {

    private String clientId;
    private boolean firstUserIsAdmin;
    private final String clientIdTag = "{client_id}";
    private final String redirectUrl = "https://github.com/login/oauth/authorize?client_id={client_id}&scope=user";

    @Autowired
    OAuth2Repository oAuth2Repository;

    @Autowired
    UserService userService;

    public GithubLoginServiceImpl(@Value("${oauth2.client-id}") String clientId,
                                  @Value("${first-user-is-admin}") boolean firstUserIsAdmin) {
        this.clientId = clientId;
        this.firstUserIsAdmin = firstUserIsAdmin;
    }

    @Override
    public String getLoginUrl() {
        return redirectUrl
                .replace(clientIdTag, clientId) + "&state=" + UUID.randomUUID();
    }

    @Override
    public String getAccessToken(String code, String state) {
        AccessTokenDataEntity accessTokenDataEntity = oAuth2Repository.getAccessToken(code, state);
        final String accessToken = accessTokenDataEntity.getAccessToken();
        final RoleEnum scope = accessTokenDataEntity.getScope();

        AuthenticatedUserDataEntity userDataEntity = oAuth2Repository.getAuthenticatedUserData(accessToken);
        final String userId = userDataEntity.getId();
        final String userName = userDataEntity.getName();

        try {
            UserDto user = userService.getUser(userId);
            return hasValidScope(user, scope)
                    ? accessToken
                    : null;
        } catch (EntityNotFoundException ex) {
            registerUser(userId, userName);
            return accessToken;
        }
    }

    private void registerUser(String userId, String userName) {
        UserDto userDto = new UserDto();
        userDto.setId(userId);
        userDto.setName(userName);
        userDto.setRole(firstUserIsAdmin && userService.countUsers() == 0
                            ? ADMIN
                            : USER);

        userService.createUser(userDto);
    }

    private boolean hasValidScope(UserDto authenticatedUserEntity, UserDto.RoleEnum scope) {
        return authenticatedUserEntity.getRole() == ADMIN
                ? true
                : scope == USER;
    }

}
