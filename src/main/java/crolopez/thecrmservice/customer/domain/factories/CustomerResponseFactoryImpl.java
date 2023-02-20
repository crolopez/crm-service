package crolopez.thecrmservice.customer.domain.factories;

import crolopez.thecrmservice.customer.domain.entities.CustomerEntity;
import crolopez.thecrmservice.shared.domain.dtos.CustomerDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerResponseFactoryImpl implements CustomerResponseFactory {
    @Override
    public List<CustomerDto> create(List<CustomerEntity> customerEntities) {
        return customerEntities.stream().map(x -> {
            var customer = new CustomerDto();
            customer.setId(x.getId().toString());
            customer.setName(x.getName());
            customer.setSurname(x.getSurname());
            customer.setLastUpdateBy(x.getLastUpdateBy());
            customer.createdBy(x.getCreatedBy());
            return customer;
        }).toList();
    }

    @Override
    public CustomerDto create(CustomerEntity customerEntity) {
        var customer = new CustomerDto();
        customer.setId(customerEntity.getId());
        customer.setName(customerEntity.getName());
        customer.setSurname(customerEntity.getSurname());
        customer.setLastUpdateBy(customerEntity.getLastUpdateBy());
        customer.createdBy(customerEntity.getCreatedBy());
        return customer;
    }
}
