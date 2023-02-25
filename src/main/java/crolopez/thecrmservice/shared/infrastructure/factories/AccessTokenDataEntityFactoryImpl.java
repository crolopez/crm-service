package crolopez.thecrmservice.shared.infrastructure.factories;

import crolopez.thecrmservice.shared.infrastructure.entities.AccessTokenDataEntity;
import crolopez.thecrmservice.shared.infrastructure.entities.dto.AccessTokenDataResponseDto;
import crolopez.thecrmservice.shared.infrastructure.entities.valueobjects.Role;
import crolopez.thecrmservice.shared.infrastructure.exceptions.InvalidScopeException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class AccessTokenDataEntityFactoryImpl implements  AccessTokenDataEntityFactory{
    @Override
    public AccessTokenDataEntity create(ResponseEntity<AccessTokenDataResponseDto> responseEntity) {
        String scope = responseEntity.getBody().getScope().toUpperCase();
        if (!scope.equals(Role.USER.toString()) && !scope.equals(Role.ADMIN.toString())) {
            throw new InvalidScopeException();
        }

        return AccessTokenDataEntity.builder()
                .accessToken(responseEntity.getBody().getAccessToken())
                .scope(Role.valueOf(scope))
                .build();
    }
}
