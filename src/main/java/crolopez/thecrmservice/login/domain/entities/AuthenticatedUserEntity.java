package crolopez.thecrmservice.login.domain.entities;

import crolopez.thecrmservice.shared.infrastructure.entities.valueobjects.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthenticatedUserEntity {
    private String userId;
    private Role role;
}
