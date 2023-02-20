package crolopez.thecrmservice.customer.domain.factories;

import crolopez.thecrmservice.customer.domain.entities.CustomerEntity;
import crolopez.thecrmservice.shared.domain.dtos.CustomerDto;
import org.springframework.stereotype.Component;

@Component
public class CustomerEntityFactoryImpl implements CustomerEntityFactory {
    @Override
    public CustomerEntity create(CustomerDto customerDto) {
        return CustomerEntity.builder()
                .id(customerDto.getId())
                .name(customerDto.getName())
                .surname(customerDto.getSurname())
                .imageUrl(customerDto.getImageUrl())
                .build();
    }
}
