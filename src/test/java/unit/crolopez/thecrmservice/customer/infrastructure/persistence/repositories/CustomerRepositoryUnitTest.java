package unit.crolopez.thecrmservice.customer.infrastructure.persistence.repositories;

import crolopez.thecrmservice.customer.domain.entities.CustomerEntity;
import crolopez.thecrmservice.customer.infrastructure.repositories.CustomerRepository;
import crolopez.thecrmservice.shared.infrastructure.persistence.mappers.Mapper;
import crolopez.thecrmservice.customer.infrastructure.entities.CustomerDbEntity;
import crolopez.thecrmservice.shared.infrastructure.persistence.unit.UnitOfWork;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerRepositoryUnitTest {
    @Mock
    Mapper mapper;

    @Mock
    UnitOfWork unitOfWork;

    @InjectMocks
    private CustomerRepository customerRepository;

    @AfterEach
    public void after() {
        Mockito.verifyNoMoreInteractions(unitOfWork, mapper);
        Mockito.reset(unitOfWork, mapper);
    }

    @Test
    public void whenCallGet_thenTheExpectedResponseIsReturned() {
        CustomerDbEntity dbEntity = Mockito.mock(CustomerDbEntity.class);
        List<CustomerDbEntity> dbEntities = Arrays.asList(dbEntity);
        CustomerEntity customerEntity = Mockito.mock(CustomerEntity.class);
        when(unitOfWork.get(CustomerDbEntity.class)).thenReturn(dbEntities);
        when(mapper.externalEntityToEntity(dbEntity)).thenReturn(customerEntity);

        List<CustomerEntity> response = customerRepository.get();

        assertEquals(1, response.stream().count());
        assertEquals(customerEntity, response.get(0));
        Mockito.verify(unitOfWork).get(CustomerDbEntity.class);
        Mockito.verify(mapper).externalEntityToEntity(dbEntity);
    }

}
