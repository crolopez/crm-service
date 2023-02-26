package crolopez.thecrmservice.login.application.services;

public interface LoginService {
    String getLoginUrl();

    String getAccessToken(String code, String state);

}
