package crolopez.thecrmservice.customer.infrastructure.controllers;

import crolopez.thecrmservice.api.ApiUtil;
import crolopez.thecrmservice.api.V1ApiDelegate;
import crolopez.thecrmservice.customer.application.services.CustomerService;
import crolopez.thecrmservice.shared.domain.dtos.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class V1ApiDelegateImpl implements V1ApiDelegate {

    @Autowired
    private CustomerService customerService;

    @Override
    public ResponseEntity<List<CustomerDto>> getCustomers() {
        return ResponseEntity.ok().body(customerService.getCustomers());
    }

    @Override
    public ResponseEntity<CustomerDto> createCustomer(CustomerDto customerDto) {
        return ResponseEntity.ok().body(customerService.createCustomer(customerDto));
    }

}
