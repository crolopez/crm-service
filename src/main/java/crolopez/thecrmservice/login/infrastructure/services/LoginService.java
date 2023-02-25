package crolopez.thecrmservice.login.infrastructure.services;

public interface LoginService {
    String getLoginUrl(String scope);

    String getAccessToken(String code, String state);
}
