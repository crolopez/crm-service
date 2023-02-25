package crolopez.thecrmservice.login.application.services;

import crolopez.thecrmservice.login.domain.entities.AuthenticatedUserEntity;

public interface LoginService {
    String getLoginUrl();

    String getAccessToken(String code, String state);

    AuthenticatedUserEntity getUser(String userId);
}
