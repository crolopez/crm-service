package crolopez.thecrmservice.customer.infrastructure.persistence.mappers;

import crolopez.thecrmservice.customer.domain.entities.CustomerEntity;
import crolopez.thecrmservice.shared.infrastructure.persistence.mappers.Mapper;
import crolopez.thecrmservice.customer.infrastructure.persistence.models.CustomerDbEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper implements Mapper<CustomerDbEntity, CustomerEntity> {

    @Override
    public CustomerEntity dbEntityToEntity(CustomerDbEntity customerDbEntity) {
        return CustomerEntity.builder()
                .id(customerDbEntity.getId())
                .name(customerDbEntity.getName())
                .surname(customerDbEntity.getSurname())
                .createdBy(customerDbEntity.getCreatedBy())
                .lastUpdateBy(customerDbEntity.getLastUpdateBy())
                .build();
    }

    @Override
    public CustomerDbEntity entityToDbEntity(CustomerEntity customerEntity) {
        return CustomerDbEntity.builder()
                .id(customerEntity.getId())
                .name(customerEntity.getName())
                .surname(customerEntity.getSurname())
                .createdBy(customerEntity.getCreatedBy())
                .lastUpdateBy(customerEntity.getLastUpdateBy())
                .build();
    }
}
