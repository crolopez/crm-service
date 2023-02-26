package crolopez.thecrmservice.shared.infrastructure.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import crolopez.thecrmservice.shared.domain.entities.dto.UserDto.RoleEnum;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccessTokenDataEntity {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("scope")
    private RoleEnum scope;

}
