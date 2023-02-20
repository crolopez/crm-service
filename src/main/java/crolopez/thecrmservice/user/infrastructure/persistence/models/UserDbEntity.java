package crolopez.thecrmservice.user.infrastructure.persistence.models;

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

    @Column(name="IS_ADMIN", nullable=false)
    private Boolean isAdmin;

    public UserDbEntity() {}
}
