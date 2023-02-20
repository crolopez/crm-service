package crolopez.thecrmservice.user.domain.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserEntity {
    private String id;

    private Boolean isAdmin;
}
