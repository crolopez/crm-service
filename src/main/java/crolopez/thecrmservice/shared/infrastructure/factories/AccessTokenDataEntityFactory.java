package crolopez.thecrmservice.shared.infrastructure.factories;

import crolopez.thecrmservice.shared.infrastructure.entities.AccessTokenDataEntity;
import crolopez.thecrmservice.shared.infrastructure.entities.AccessTokenDataResponseDto;
import org.springframework.http.ResponseEntity;

public interface AccessTokenDataEntityFactory {
    AccessTokenDataEntity create(ResponseEntity<AccessTokenDataResponseDto> responseEntity);
}
