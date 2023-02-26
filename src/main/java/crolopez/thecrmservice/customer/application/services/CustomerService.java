package crolopez.thecrmservice.customer.application.services;

import crolopez.thecrmservice.shared.domain.entities.dto.CustomerDto;

import java.util.List;

public interface CustomerService {
    List<CustomerDto> getCustomers();

    CustomerDto createCustomer(CustomerDto customerDto, String loggedUserId);

    CustomerDto getCustomer(String id);

    CustomerDto deleteCustomer(String id);

    CustomerDto updateCustomer(String id, CustomerDto customerDto, String loggedUserId);
}
