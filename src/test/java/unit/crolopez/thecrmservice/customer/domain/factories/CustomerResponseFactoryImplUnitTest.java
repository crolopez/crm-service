package unit.crolopez.thecrmservice.customer.domain.factories;

import crolopez.thecrmservice.customer.domain.entities.CustomerEntity;
import crolopez.thecrmservice.customer.domain.factories.CustomerResponseFactoryImpl;
import crolopez.thecrmservice.shared.domain.entities.dto.CustomerDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CustomerResponseFactoryImplUnitTest {
    @InjectMocks
    private CustomerResponseFactoryImpl factory;

    @Test
    public void givenValidEntity_whenCallCreate_thenTheExpectedResponseIsReturned() {
        CustomerEntity entity = CustomerEntity.builder()
                .id("fakeId")
                .createdBy("fakeCreatedBy")
                .name("fakeName")
                .surname("fakeSurname")
                .imageUrl("fakeImageUrl")
                .lastUpdateBy("fakeLastUpdateBy")
                .build();

        CustomerDto response = factory.create(entity);

        assertEquals("fakeId", response.getId());
        assertEquals("fakeCreatedBy", response.getCreatedBy());
        assertEquals("fakeName", response.getName());
        assertEquals("fakeSurname", response.getSurname());
        assertEquals("fakeImageUrl", response.getImageUrl());
        assertEquals("fakeLastUpdateBy", response.getLastUpdateBy());
    }

    @Test
    public void givenValidListOfEntities_whenCallCreate_thenTheExpectedResponseIsReturned() {
        List<CustomerEntity> entities = List.of(CustomerEntity.builder()
                .id("fakeId")
                .createdBy("fakeCreatedBy")
                .name("fakeName")
                .surname("fakeSurname")
                .imageUrl("fakeImageUrl")
                .lastUpdateBy("fakeLastUpdateBy")
                .build());

        List<CustomerDto> response = factory.create(entities);

        assertEquals("fakeId", response.get(0).getId());
        assertEquals("fakeCreatedBy", response.get(0).getCreatedBy());
        assertEquals("fakeName", response.get(0).getName());
        assertEquals("fakeSurname", response.get(0).getSurname());
        assertEquals("fakeImageUrl", response.get(0).getImageUrl());
        assertEquals("fakeLastUpdateBy", response.get(0).getLastUpdateBy());
    }
}
