package unit.crolopez.thecrmservice.customer.application.services;

import crolopez.thecrmservice.customer.application.services.CustomerServiceImpl;
import crolopez.thecrmservice.customer.domain.entities.CustomerEntity;
import crolopez.thecrmservice.customer.domain.factories.CustomerResponseFactory;
import crolopez.thecrmservice.customer.infrastructure.repositories.CustomerRepository;
import crolopez.thecrmservice.shared.entities.CustomerDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplUnitTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerResponseFactory customerResponseFactory;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @AfterEach
    public void after() {
        Mockito.verifyNoMoreInteractions(customerResponseFactory, customerRepository);
        Mockito.reset(customerResponseFactory, customerRepository);
    }

    @Test
    public void whenGetCustomers_thenTheExpectedResponseIsReturned() {
        var customerEntity = Mockito.mock(CustomerEntity.class);
        List<CustomerEntity> customerEntities = List.of(customerEntity);
        CustomerDto customerDto = Mockito.mock(CustomerDto.class);
        List<CustomerDto> customerDtos = List.of(customerDto);
        when(customerRepository.get()).thenReturn(customerEntities);
        when(customerResponseFactory.create(customerEntities)).thenReturn(customerDtos);

        List<CustomerDto> response = customerService.getCustomers();

        assertEquals(customerDtos, response);
        Mockito.verify(customerRepository).get();
        Mockito.verify(customerResponseFactory).create(customerEntities);
    }

}
