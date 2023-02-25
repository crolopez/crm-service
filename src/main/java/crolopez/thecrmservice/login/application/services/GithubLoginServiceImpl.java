package crolopez.thecrmservice.login.application.services;

import crolopez.thecrmservice.login.domain.entities.AuthenticatedUserEntity;
import crolopez.thecrmservice.login.domain.factories.AuthenticatedUserEntityFactory;
import crolopez.thecrmservice.shared.application.repositories.PersistenceRepository;
import crolopez.thecrmservice.shared.infrastructure.entities.AccessTokenDataEntity;
import crolopez.thecrmservice.shared.infrastructure.entities.AuthenticatedUserDataEntity;
import crolopez.thecrmservice.shared.infrastructure.entities.valueobjects.Role;
import crolopez.thecrmservice.shared.infrastructure.repositories.OAuth2Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@Component
public class GithubLoginServiceImpl implements LoginService {

    private String clientId;
    private boolean firstUserIsAdmin;
    private final String clientIdTag = "{client_id}";
    private final String redirectUrl = "https://github.com/login/oauth/authorize?client_id={client_id}&scope=user";

    @Autowired
    AuthenticatedUserEntityFactory authenticatedUserEntityFactory;

    @Autowired
    OAuth2Repository oAuth2Repository;

    @Autowired
    private PersistenceRepository<AuthenticatedUserEntity> authenticatedUserRepository;

    public GithubLoginServiceImpl(@Value("${oauth2.client-id}") String clientId,
                                  @Value("${oauth2.first-user-is-admin}") boolean firstUserIsAdmin) {
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
        final Role scope = accessTokenDataEntity.getScope();

        AuthenticatedUserDataEntity userDataEntity = oAuth2Repository.getAuthenticatedUserData(accessToken);
        final String userId = userDataEntity.getId();

        try {
            AuthenticatedUserEntity authenticatedUserEntity = authenticatedUserRepository.get(userId);
            return hasValidScope(authenticatedUserEntity, scope)
                    ? accessToken
                    : null;
        } catch (EntityNotFoundException ex) {
            registerUser(userId);
            return accessToken;
        }
    }

    @Override
    public AuthenticatedUserEntity getUser(String userId) {
        return authenticatedUserRepository.get(userId);
    }

    private void registerUser(String userId) {
        AuthenticatedUserEntity user = firstUserIsAdmin && authenticatedUserRepository.count() == 0
            ? authenticatedUserEntityFactory.create(userId, Role.ADMIN)
            : authenticatedUserEntityFactory.create(userId, Role.USER);
        authenticatedUserRepository.create(user);
    }

    private boolean hasValidScope(AuthenticatedUserEntity authenticatedUserEntity, Role scope) {
        return authenticatedUserEntity.getRole() == Role.ADMIN
                ? true
                : scope == Role.USER;
    }

}
