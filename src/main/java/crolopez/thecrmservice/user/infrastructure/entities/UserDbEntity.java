package crolopez.thecrmservice.user.infrastructure.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "USER_")
@Getter
@AllArgsConstructor
@Builder
public class UserDbEntity {

    @Id
    @Column(name="ID", nullable=false, unique=true)
    private String id;

    @Column(name="ROLE", nullable=false)
    private String role;

    @Column(name="NAME")
    private String name;

    public UserDbEntity() {}
}
