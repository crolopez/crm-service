package crolopez.thecrmservice.shared.infrastructure.entities;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthenticatedUserDataEntity {
    String username;
    String scope;
}
