package crolopez.thecrmservice.customer.application.services;

import crolopez.thecrmservice.shared.domain.dtos.CustomerDto;

import java.util.List;

public interface CustomerService {
    List<CustomerDto> getCustomers();

    CustomerDto createCustomer(CustomerDto customerDto);

    CustomerDto getCustomer(String id);

    CustomerDto deleteCustomer(String id);
}
