package crolopez.thecrmservice.customer.domain.factories;

import crolopez.thecrmservice.customer.domain.entities.CustomerEntity;
import crolopez.thecrmservice.shared.entities.CustomerDto;

import java.util.List;

public interface CustomerResponseFactory {
    List<CustomerDto> create(List<CustomerEntity> customerEntities);
    CustomerDto create(CustomerEntity customerEntity);
}