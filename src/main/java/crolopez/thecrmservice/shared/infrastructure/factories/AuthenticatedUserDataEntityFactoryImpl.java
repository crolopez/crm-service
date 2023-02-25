package crolopez.thecrmservice.shared.infrastructure.factories;

import crolopez.thecrmservice.shared.infrastructure.entities.AuthenticatedUserDataEntity;
import crolopez.thecrmservice.shared.infrastructure.entities.dto.AuthUserResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedUserDataEntityFactoryImpl implements AuthenticatedUserDataEntityFactory {
    @Override
    public AuthenticatedUserDataEntity create(ResponseEntity<AuthUserResponseDto> response) {
        return AuthenticatedUserDataEntity.builder()
                .username(response.getBody().getLogin())
                .id(response.getBody().getId())
                .scope(response.getHeaders().get("X-OAuth-Scopes").get(0).toUpperCase())
                .build();
    }
}
