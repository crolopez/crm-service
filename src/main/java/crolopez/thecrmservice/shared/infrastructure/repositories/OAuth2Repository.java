package crolopez.thecrmservice.shared.infrastructure.repositories;

import crolopez.thecrmservice.shared.infrastructure.entities.AccessTokenDataEntity;
import crolopez.thecrmservice.shared.infrastructure.entities.AccessTokenDataResponseDto;
import crolopez.thecrmservice.shared.infrastructure.entities.AuthenticatedUserDataEntity;

public interface OAuth2Repository {
    AuthenticatedUserDataEntity getAuthenticatedUserData(String token);

    AccessTokenDataEntity getAccessToken(String code, String state);
}
