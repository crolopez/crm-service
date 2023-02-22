package crolopez.thecrmservice.login.infrastructure.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
public class GithubLoginServiceImpl implements LoginService {

    private String clientId;
    private String clientSecret;
    private final String scopeTag = "{scope}";
    private final String clientIdTag = "{client_id}";
    private final String accessTokenUrl = "https://github.com/login/oauth/access_token";
    private final String redirectUrl = "https://github.com/login/oauth/authorize?client_id={client_id}&scope={scope}";

    public GithubLoginServiceImpl(@Value("${oauth2.client-id}") String clientId,
                                  @Value("${oauth2.client-secret}") String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    @Override
    public String login(String scope) {
        return redirectUrl
                .replace(clientIdTag, clientId)
                .replace(scopeTag, scope) + "&state=" + UUID.randomUUID();
    }

    @Override
    public String getAccessToken(String code, String state) {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("code", code);
        body.add("state", state);

        var restTemplate = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(accessTokenUrl, requestEntity, String.class);

        String responseBody = responseEntity.getBody();
        String[] tokenParts = responseBody.split("&");
        String accessToken = tokenParts[0].split("=")[1];
        String scope = tokenParts[1].split("=")[1];

        return accessToken;
    }
}
