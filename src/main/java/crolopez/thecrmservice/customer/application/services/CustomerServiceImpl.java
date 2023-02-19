package crolopez.thecrmservice.customer.application.services;

import crolopez.thecrmservice.customer.domain.entities.CustomerEntity;
import crolopez.thecrmservice.customer.domain.factories.CustomerResponseFactory;
import crolopez.thecrmservice.shared.application.repositories.Repository;
import crolopez.thecrmservice.shared.domain.dtos.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private Repository<CustomerEntity> customerEntityRepository;

    @Autowired
    private CustomerResponseFactory customerResponseFactory;

    @Override
    public List<CustomerDto> getCustomers() {
        List<CustomerEntity> customerEntities = customerEntityRepository.get();
        return customerResponseFactory.create(customerEntities);
    }
}
