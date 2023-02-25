package crolopez.thecrmservice.customer.infrastructure.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity(name = "CUSTOMER")
@Getter
@AllArgsConstructor
@Builder
public class CustomerDbEntity {

    @Id
    @Column(name="ID", nullable=false, unique=true)
    private String id;

    @Column(name="NAME", nullable=false)
    private String name;

    @Column(name="SURNAME", nullable=false)
    private String surname;

    @Column(name="CREATED_BY", nullable=false)
    private String createdBy;

    @Column(name="LAST_UPDATED_BY", nullable=true)
    private String lastUpdateBy;

    @Column(name="IMAGE_URL", nullable=true)
    private String imageUrl;

    public CustomerDbEntity() {}
}
