package crolopez.thecrmservice.login.infrastructure.entities;

import crolopez.thecrmservice.shared.infrastructure.entities.valueobjects.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "AUTHENTICATED_USER")
@Getter
@AllArgsConstructor
@Builder
public class AuthenticatedUserDbEntity {
    @Id
    @Column(name="ID", nullable=false, unique=true)
    private String id;

    @Column(name="ROLE", nullable=false)
    private String role;

    public AuthenticatedUserDbEntity() {}
}
