package unit.crolopez.thecrmservice.customer.application.services;

import crolopez.thecrmservice.customer.application.services.CustomerServiceImpl;
import crolopez.thecrmservice.customer.domain.entities.CustomerEntity;
import crolopez.thecrmservice.customer.domain.factories.CustomerEntityFactory;
import crolopez.thecrmservice.customer.domain.factories.CustomerResponseFactory;
import crolopez.thecrmservice.customer.infrastructure.repositories.CustomerRepository;
import crolopez.thecrmservice.shared.domain.entities.dto.CustomerDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplUnitTest {

    final String fakeLoggedUser = "fakeLoggedUser";

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerResponseFactory customerResponseFactory;

    @Mock
    private CustomerEntityFactory customerEntityFactory;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @AfterEach
    public void after() {
        Mockito.verifyNoMoreInteractions(customerResponseFactory, customerRepository, customerEntityFactory);
        Mockito.reset(customerResponseFactory, customerRepository, customerEntityFactory);
    }

    @Test
    public void whenCallGetCustomers_thenTheExpectedResponseIsReturned() {
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

    @Test
    public void whenCallCreateCustomer_thenTheExpectedResponseIsReturned() {
        var customerEntity = Mockito.mock(CustomerEntity.class);
        CustomerDto customerDto = Mockito.mock(CustomerDto.class);
        CustomerDto customerResponseDto = Mockito.mock(CustomerDto.class);
        when(customerEntityFactory.create(customerDto, fakeLoggedUser)).thenReturn(customerEntity);
        when(customerRepository.create(customerEntity)).thenReturn(customerEntity);
        when(customerResponseFactory.create(customerEntity)).thenReturn(customerResponseDto);

        CustomerDto response = customerService.createCustomer(customerDto, fakeLoggedUser);

        assertEquals(customerResponseDto, response);
        Mockito.verify(customerRepository).create(customerEntity);
        Mockito.verify(customerEntityFactory).create(customerDto, fakeLoggedUser);
        Mockito.verify(customerResponseFactory).create(customerEntity);
    }


    @Test
    public void whenCallGetCustomer_thenTheExpectedResponseIsReturned() {
        var customerEntity = Mockito.mock(CustomerEntity.class);
        CustomerDto customerResponseDto = Mockito.mock(CustomerDto.class);
        when(customerRepository.get("fakeId")).thenReturn(customerEntity);
        when(customerResponseFactory.create(customerEntity)).thenReturn(customerResponseDto);

        CustomerDto response = customerService.getCustomer("fakeId");

        assertEquals(customerResponseDto, response);
        Mockito.verify(customerRepository).get("fakeId");
        Mockito.verify(customerResponseFactory).create(customerEntity);
    }

    @Test
    public void whenCallDeleteCustomer_thenTheExpectedResponseIsReturned() {
        var customerEntity = Mockito.mock(CustomerEntity.class);
        CustomerDto customerResponseDto = Mockito.mock(CustomerDto.class);
        when(customerRepository.get("fakeId")).thenReturn(customerEntity);
        when(customerResponseFactory.create(customerEntity)).thenReturn(customerResponseDto);

        CustomerDto response = customerService.deleteCustomer("fakeId");

        assertEquals(customerResponseDto, response);
        Mockito.verify(customerRepository).delete("fakeId");
        Mockito.verify(customerResponseFactory).create(customerEntity);
    }

    @Test
    public void whenCallUpdateCustomer_thenTheExpectedResponseIsReturned() {
        var customerEntity = Mockito.mock(CustomerEntity.class);
        CustomerDto customerDto = Mockito.mock(CustomerDto.class);
        CustomerDto customerResponseDto = Mockito.mock(CustomerDto.class);
        when(customerRepository.get("fakeId")).thenReturn(customerEntity);
        when(customerRepository.update("fakeId", customerEntity)).thenReturn(customerEntity);
        when(customerResponseFactory.create(customerEntity)).thenReturn(customerResponseDto);

        CustomerDto response = customerService.updateCustomer("fakeId", customerDto, fakeLoggedUser);

        assertEquals(customerResponseDto, response);
        Mockito.verify(customerRepository).update("fakeId", customerEntity);
        Mockito.verify(customerResponseFactory).create(customerEntity);
    }

}
