package crolopez.thecrmservice.shared.infrastructure.factories;

import crolopez.thecrmservice.shared.infrastructure.entities.AccessTokenDataEntity;
import crolopez.thecrmservice.shared.infrastructure.entities.AccessTokenDataResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class AccessTokenDataEntityFactoryImpl implements  AccessTokenDataEntityFactory{
    @Override
    public AccessTokenDataEntity create(ResponseEntity<AccessTokenDataResponseDto> responseEntity) {
        return AccessTokenDataEntity.builder()
                .accessToken(responseEntity.getBody().getAccessToken())
                .scope(responseEntity.getBody().getScope())
                .build();
    }
}
