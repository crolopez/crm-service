package crolopez.thecrmservice.customer.domain.factories;

import crolopez.thecrmservice.customer.domain.entities.CustomerEntity;
import crolopez.thecrmservice.shared.domain.dtos.CustomerDto;

import java.util.List;

public interface CustomerEntityFactory {
    CustomerEntity create(CustomerDto customerDto);
}