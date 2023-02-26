package unit.crolopez.thecrmservice.customer.infrastructure.persistence.mappers;

import crolopez.thecrmservice.customer.domain.entities.CustomerEntity;
import crolopez.thecrmservice.customer.infrastructure.entities.CustomerDbEntity;
import crolopez.thecrmservice.customer.infrastructure.persistence.mappers.CustomerDbMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CustomerDbMapperUnitTest {

    @InjectMocks
    private CustomerDbMapper mapper;

    @Test
    public void givenValidEntity_whenCallEntityToExternalEntity_thenTheExpectedResponseIsReturned() {
        CustomerEntity entity = CustomerEntity.builder()
                .id("fakeId")
                .createdBy("fakeCreatedBy")
                .name("fakeName")
                .surname("fakeSurname")
                .imageUrl("fakeImageUrl")
                .lastUpdateBy("fakeLastUpdateBy")
                .build();

        CustomerDbEntity response = mapper.entityToExternalEntity(entity);

        assertEquals("fakeId", response.getId());
        assertEquals("fakeCreatedBy", response.getCreatedBy());
        assertEquals("fakeName", response.getName());
        assertEquals("fakeSurname", response.getSurname());
        assertEquals("fakeImageUrl", response.getImageUrl());
        assertEquals("fakeLastUpdateBy", response.getLastUpdateBy());
    }

    @Test
    public void givenValidDbEntity_whenCallExternalEntityToEntity_thenTheExpectedResponseIsReturned() {
        CustomerDbEntity customerDbEntity = CustomerDbEntity.builder()
            .id("fakeId")
            .createdBy("fakeCreatedBy")
            .name("fakeName")
            .surname("fakeSurname")
            .imageUrl("fakeImageUrl")
            .lastUpdateBy("fakeLastUpdateBy").build();

        CustomerEntity response = mapper.externalEntityToEntity(customerDbEntity);

        assertEquals("fakeId", response.getId());
        assertEquals("fakeCreatedBy", response.getCreatedBy());
        assertEquals("fakeName", response.getName());
        assertEquals("fakeSurname", response.getSurname());
        assertEquals("fakeImageUrl", response.getImageUrl());
        assertEquals("fakeLastUpdateBy", response.getLastUpdateBy());
    }

}
