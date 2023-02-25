package unit.crolopez.thecrmservice.shared.infrastructure.controllers;

import crolopez.thecrmservice.customer.application.services.CustomerService;
import crolopez.thecrmservice.shared.infrastructure.controllers.V1ApiDelegateImpl;
import crolopez.thecrmservice.shared.entities.CustomerDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class V1ApiDelegateImplUnitTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private V1ApiDelegateImpl v1ApiDelegate;

    @AfterEach
    public void after() {
        Mockito.verifyNoMoreInteractions(customerService);
        Mockito.reset(customerService);
    }

    @Test
    public void whenGetCustomersIsCalled_thenTheExpectedResponseIsReturned() {
        CustomerDto customer = Mockito.mock(CustomerDto.class);
        List<CustomerDto> customers = List.of(customer);
        when(customerService.getCustomers()).thenReturn(customers);

        ResponseEntity<List<CustomerDto>> responseEntity = v1ApiDelegate.getCustomers();

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(1, responseEntity.getBody().stream().count());
        assertEquals(customers, responseEntity.getBody());
        Mockito.verify(customerService).getCustomers();
    }
}
