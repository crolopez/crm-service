package crolopez.thecrmservice.shared.infrastructure.factories;

import crolopez.thecrmservice.shared.domain.entities.dto.UserDto;
import crolopez.thecrmservice.shared.infrastructure.entities.AccessTokenDataEntity;
import crolopez.thecrmservice.shared.infrastructure.entities.dto.AccessTokenDataResponseDto;
import crolopez.thecrmservice.shared.infrastructure.exceptions.InvalidScopeException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import static crolopez.thecrmservice.shared.domain.entities.dto.UserDto.RoleEnum.ADMIN;
import static crolopez.thecrmservice.shared.domain.entities.dto.UserDto.RoleEnum.USER;

@Component
public class AccessTokenDataEntityFactoryImpl implements  AccessTokenDataEntityFactory{
    @Override
    public AccessTokenDataEntity create(ResponseEntity<AccessTokenDataResponseDto> responseEntity) {
        String scope = responseEntity.getBody().getScope().toUpperCase();
        if (!scope.equals(USER.toString()) && !scope.equals(ADMIN.toString())) {
            throw new InvalidScopeException();
        }

        return AccessTokenDataEntity.builder()
                .accessToken(responseEntity.getBody().getAccessToken())
                .scope(UserDto.RoleEnum.valueOf(scope))
                .build();
    }
}
