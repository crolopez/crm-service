package crolopez.thecrmservice.customer.domain.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CustomerEntity {
    private String id;

    private String name;

    private String surname;

    private String lastUpdateBy;

    private String createdBy;
}
