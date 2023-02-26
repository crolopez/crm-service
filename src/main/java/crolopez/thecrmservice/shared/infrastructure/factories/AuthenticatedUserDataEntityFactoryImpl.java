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
                .name(response.getBody().getName())
                .id(response.getBody().getId())
                .build();
    }
}
