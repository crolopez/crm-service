package crolopez.thecrmservice.customer.application.services;

import crolopez.thecrmservice.customer.domain.entities.CustomerEntity;
import crolopez.thecrmservice.customer.domain.factories.CustomerEntityFactory;
import crolopez.thecrmservice.customer.domain.factories.CustomerResponseFactory;
import crolopez.thecrmservice.shared.application.repositories.PersistenceRepository;
import crolopez.thecrmservice.shared.domain.entities.dto.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private PersistenceRepository<CustomerEntity> customerEntityRepository;

    @Autowired
    private CustomerResponseFactory customerResponseFactory;

    @Autowired
    private CustomerEntityFactory customerEntityFactory;

    @Override
    public List<CustomerDto> getCustomers() {
        List<CustomerEntity> customerEntities = customerEntityRepository.get();
        return customerResponseFactory.create(customerEntities);
    }

    @Override
    public CustomerDto createCustomer(CustomerDto customerDto, String loggedUserId) {
        CustomerEntity customerEntity = customerEntityFactory.create(customerDto, loggedUserId);
        customerEntity = customerEntityRepository.create(customerEntity);
        return customerResponseFactory.create(customerEntity);
    }

    @Override
    public CustomerDto getCustomer(String id) {
        CustomerEntity customerEntity = customerEntityRepository.get(id);
        return customerResponseFactory.create(customerEntity);
    }

    @Override
    public CustomerDto deleteCustomer(String id) {
        CustomerEntity customerEntity = customerEntityRepository.get(id);
        customerEntityRepository.delete(id);
        return customerResponseFactory.create(customerEntity);
    }

    @Override
    public CustomerDto updateCustomer(String id, CustomerDto customerDto, String loggedUserId) {
        CustomerEntity customerEntity = customerEntityRepository.get(id);
        customerEntity.setId(customerDto.getId());
        customerEntity.setName(customerDto.getName());
        customerEntity.setImageUrl(customerDto.getImageUrl());
        customerEntity.setSurname(customerDto.getSurname());
        customerEntity.setLastUpdateBy(loggedUserId);
        customerEntity = customerEntityRepository.update(id, customerEntity);
        return customerResponseFactory.create(customerEntity);
    }
}
