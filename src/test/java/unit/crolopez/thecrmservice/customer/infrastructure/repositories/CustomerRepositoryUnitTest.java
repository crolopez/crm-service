package unit.crolopez.thecrmservice.customer.infrastructure.repositories;

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

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Test
    public void givenAValidId_whenCallGet_thenTheExpectedResponseIsReturned() {
        CustomerDbEntity dbEntity = Mockito.mock(CustomerDbEntity.class);
        List<CustomerDbEntity> dbEntities = Arrays.asList(dbEntity);
        CustomerEntity customerEntity = Mockito.mock(CustomerEntity.class);
        when(unitOfWork.get("id", "validId", CustomerDbEntity.class)).thenReturn(dbEntities);
        when(mapper.externalEntityToEntity(dbEntity)).thenReturn(customerEntity);

        CustomerEntity response = customerRepository.get("validId");

        assertEquals(customerEntity, response);
        Mockito.verify(unitOfWork).get("id", "validId", CustomerDbEntity.class);
        Mockito.verify(mapper).externalEntityToEntity(dbEntity);
    }

    @Test
    public void givenAnNonExistingId_whenCallGet_thenTheExpectedExceptionIsThrown() {
        CustomerDbEntity dbEntity = Mockito.mock(CustomerDbEntity.class);
        CustomerEntity customerEntity = Mockito.mock(CustomerEntity.class);
        when(unitOfWork.get("id", "nonExistingId", CustomerDbEntity.class)).thenThrow(new ArrayIndexOutOfBoundsException());
        when(mapper.externalEntityToEntity(dbEntity)).thenReturn(customerEntity);

        assertThrows(EntityNotFoundException.class, () -> customerRepository.get("nonExistingId"));
    }

    @Test
    public void givenAValidId_whenCallDelete_thenTheExpectedResponseIsReturned() {
        customerRepository.delete("validId");

        Mockito.verify(unitOfWork).delete("validId", CustomerDbEntity.class);
    }

    @Test
    public void whenCallCreate_thenTheExpectedResponseIsReturned() {
        CustomerDbEntity dbEntity = Mockito.mock(CustomerDbEntity.class);
        CustomerEntity customerEntity = Mockito.mock(CustomerEntity.class);
        when(mapper.entityToExternalEntity(customerEntity)).thenReturn(dbEntity);

        CustomerEntity response = customerRepository.create(customerEntity);

        assertEquals(customerEntity, response);
        Mockito.verify(unitOfWork).create(dbEntity);
        Mockito.verify(mapper).entityToExternalEntity(customerEntity);
    }

    @Test
    public void whenCallUpdate_thenTheExpectedResponseIsReturned() {
        CustomerDbEntity dbEntity = Mockito.mock(CustomerDbEntity.class);
        CustomerEntity customerEntity = Mockito.mock(CustomerEntity.class);
        when(mapper.entityToExternalEntity(customerEntity)).thenReturn(dbEntity);

        CustomerEntity response = customerRepository.update("fakeId", customerEntity);

        assertEquals(customerEntity, response);
        Mockito.verify(unitOfWork).update("fakeId", dbEntity, CustomerDbEntity.class);
        Mockito.verify(mapper).entityToExternalEntity(customerEntity);
    }

    @Test
    public void whenCallCount_thenTheExpectedResponseIsReturned() {
        when(unitOfWork.count(CustomerDbEntity.class)).thenReturn(49L);

        Long response = customerRepository.count();

        assertEquals(49L, response);
        Mockito.verify(unitOfWork).count(CustomerDbEntity.class);
    }

}
