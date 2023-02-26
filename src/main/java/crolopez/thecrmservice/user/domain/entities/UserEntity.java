package crolopez.thecrmservice.user.domain.entities;

import crolopez.thecrmservice.shared.domain.entities.dto.UserDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserEntity {
    private String id;

    private UserDto.RoleEnum role;
}
