package crolopez.thecrmservice.shared.infrastructure.entities.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class AuthUserResponseDto {
    @JsonProperty("login")
    private String login;

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;
}

