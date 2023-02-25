package crolopez.thecrmservice.shared.infrastructure.factories;

import crolopez.thecrmservice.shared.infrastructure.entities.AuthUserResponseDto;
import crolopez.thecrmservice.shared.infrastructure.entities.AuthenticatedUserDataEntity;
import org.springframework.http.ResponseEntity;

public interface AuthenticatedUserDataEntityFactory {
    AuthenticatedUserDataEntity create(ResponseEntity<AuthUserResponseDto> response);
}