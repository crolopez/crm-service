package crolopez.thecrmservice.shared.infrastructure.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import crolopez.thecrmservice.shared.infrastructure.entities.valueobjects.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccessTokenDataEntity {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("scope")
    private Role scope;

}
