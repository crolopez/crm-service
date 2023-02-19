package unit.crolopez.thecrmservice.customers.infrastructure.controllers;

import crolopez.thecrmservice.customers.infrastructure.controllers.V1ApiDelegateImpl;
import crolopez.thecrmservice.model.CustomerDTODto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class V1ApiDelegateImplUnitTest {

    @InjectMocks
    private V1ApiDelegateImpl v1ApiDelegate;

    @Test
    public void whenGetCustomersIsCalled_thenTheExpectedResponseIsReturned() {
        ResponseEntity<List<CustomerDTODto>> responseEntity = v1ApiDelegate.getCustomers();


        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(0, responseEntity.getBody().stream().count());
    }
}
