package unit.crolopez.thecrmservice.customer.domain.factories;

import crolopez.thecrmservice.customer.domain.entities.CustomerEntity;
import crolopez.thecrmservice.customer.domain.factories.CustomerEntityFactoryImpl;
import crolopez.thecrmservice.shared.domain.entities.dto.CustomerDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CustomerEntityFactoryImplUnitTest {
    @InjectMocks
    private CustomerEntityFactoryImpl factory;

    @Test
    public void givenValidDto_whenCallCreate_thenTheExpectedResponseIsReturned() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId("fakeId");
        customerDto.setCreatedBy("fakeCreatedBy");
        customerDto.setName("fakeName");
        customerDto.setSurname("fakeSurname");
        customerDto.setImageUrl("fakeImageUrl");
        customerDto.setLastUpdateBy("fakeLastUpdateBy");

        CustomerEntity response = factory.create(customerDto, "fakeCreatedBy");

        assertEquals("fakeId", response.getId());
        assertEquals("fakeCreatedBy", response.getCreatedBy());
        assertEquals("fakeName", response.getName());
        assertEquals("fakeSurname", response.getSurname());
        assertEquals("fakeImageUrl", response.getImageUrl());
        assertEquals(null, response.getLastUpdateBy());
    }

}
