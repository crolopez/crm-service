package crolopez.thecrmservice.login.infrastructure.services;

import crolopez.thecrmservice.shared.infrastructure.entities.AccessTokenDataEntity;
import crolopez.thecrmservice.shared.infrastructure.entities.AccessTokenDataResponseDto;
import crolopez.thecrmservice.shared.infrastructure.repositories.OAuth2Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GithubLoginServiceImpl implements LoginService {

    private String clientId;
    private final String scopeTag = "{scope}";
    private final String clientIdTag = "{client_id}";
    private final String redirectUrl = "https://github.com/login/oauth/authorize?client_id={client_id}&scope={scope}";

    @Autowired
    OAuth2Repository oAuth2Repository;

    public GithubLoginServiceImpl(@Value("${oauth2.client-id}") String clientId) {
        this.clientId = clientId;
    }

    @Override
    public String getLoginUrl(String scope) {
        return redirectUrl
                .replace(clientIdTag, clientId)
                .replace(scopeTag, scope) + "&state=" + UUID.randomUUID();
    }

    @Override
    public String getAccessToken(String code, String state) {
        AccessTokenDataEntity accessTokenResponseDto = oAuth2Repository.getAccessToken(code, state);
        return accessTokenResponseDto.getAccessToken();
    }

}
