package unit.crolopez.thecrmservice.shared.infrastructure.controllers;

import crolopez.thecrmservice.customer.application.services.CustomerService;
import crolopez.thecrmservice.shared.domain.entities.dto.UserDto;
import crolopez.thecrmservice.shared.infrastructure.auth.AuthenticatedUserCache;
import crolopez.thecrmservice.shared.infrastructure.controllers.V1ApiDelegateImpl;
import crolopez.thecrmservice.shared.domain.entities.dto.CustomerDto;
import crolopez.thecrmservice.shared.infrastructure.controllers.utils.RequestManager;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class V1ApiDelegateImplUnitTest {

    private String dummyToken = "asdfg";
    private String fakeLoggedUser = "fakeUser";

    @Mock
    private UserDto fakeUser;

    @Mock
    private CustomerService customerService;

    @Mock
    private RequestManager requestManager;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private AuthenticatedUserCache cache;

    @InjectMocks
    private V1ApiDelegateImpl v1ApiDelegate;

    @BeforeEach
    public void before() {
        when(cache.getAuthenticatedUser(dummyToken)).thenReturn(fakeUser);
        when(httpServletRequest.getHeader("authorization")).thenReturn("Bearer " + dummyToken);
        when(requestManager.getCurrentRequest()).thenReturn(httpServletRequest);
    }

    @AfterEach
    public void after() {
        Mockito.verifyNoMoreInteractions(customerService, requestManager, cache, httpServletRequest);
        Mockito.reset(customerService, requestManager, cache, httpServletRequest);
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
        Mockito.verify(cache).getAuthenticatedUser(dummyToken);
    }
}
