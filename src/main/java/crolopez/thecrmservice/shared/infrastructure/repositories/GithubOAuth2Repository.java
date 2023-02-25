package crolopez.thecrmservice.shared.infrastructure.repositories;

import crolopez.thecrmservice.shared.infrastructure.entities.*;
import crolopez.thecrmservice.shared.infrastructure.factories.AccessTokenDataEntityFactory;
import crolopez.thecrmservice.shared.infrastructure.factories.AuthenticatedUserDataEntityFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component
public class GithubOAuth2Repository implements OAuth2Repository {

    private String clientId;

    private String clientSecret;

    private final String userInfoUrl = "https://api.github.com/user";

    private final String accessTokenUrl = "https://github.com/login/oauth/access_token";

    private final RestTemplate restTemplate;

    @Autowired
    AuthenticatedUserDataEntityFactory authenticatedUserDataEntityFactory;

    @Autowired
    AccessTokenDataEntityFactory accessTokenDataEntityFactory;

    public GithubOAuth2Repository(@Value("${oauth2.client-id}") String clientId,
                                  @Value("${oauth2.client-secret}") String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.restTemplate = new RestTemplate();
    }

    @Override
    public AuthenticatedUserDataEntity getAuthenticatedUserData(String token) {
        HttpHeaders headers = getRequestHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> requestHeaders = new HttpEntity<>(headers);
        ResponseEntity<AuthUserResponseDto> response = restTemplate.exchange(
                userInfoUrl, HttpMethod.GET, requestHeaders, AuthUserResponseDto.class);

        return authenticatedUserDataEntityFactory.create(response);
    }

    @Override
    public AccessTokenDataEntity getAccessToken(String code, String state) {
        HttpHeaders headers = getRequestHeaders();
        var accessTokenRequest = AccessTokenRequestDto.builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .code(code)
                .state(state)
                .build();

        HttpEntity<AccessTokenRequestDto> requestEntity = new HttpEntity(accessTokenRequest, headers);
        ResponseEntity<AccessTokenDataResponseDto> responseEntity = restTemplate
                .postForEntity(accessTokenUrl, requestEntity, AccessTokenDataResponseDto.class);

        return accessTokenDataEntityFactory.create(responseEntity);
    }

    private HttpHeaders getRequestHeaders() {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }
}
