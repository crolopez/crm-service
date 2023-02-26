package crolopez.thecrmservice.customer.domain.factories;

import crolopez.thecrmservice.customer.domain.entities.CustomerEntity;
import crolopez.thecrmservice.shared.domain.entities.dto.CustomerDto;

public interface CustomerEntityFactory {
    CustomerEntity create(CustomerDto customerDto, String loggedUserId);
}