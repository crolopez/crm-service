package crolopez.thecrmservice.login.infrastructure.services;

public interface LoginService {
    String login(String scope);

    String getAccessToken(String code, String state);
}
