package crolopez.thecrmservice.shared.infrastructure.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.bind.annotation.RequestBody;

@Getter
@Builder
public class AccessTokenRequestDto {
    @JsonProperty("client_id")
    private String clientId;

    @JsonProperty("client_secret")
    private String clientSecret;

    @JsonProperty("code")
    private String code;

    @JsonProperty("state")
    private String state;
}
