package crolopez.thecrmservice.customer.infrastructure.persistence.mappers;

import crolopez.thecrmservice.customer.domain.entities.CustomerEntity;
import crolopez.thecrmservice.shared.infrastructure.persistence.mappers.DbMapper;
import crolopez.thecrmservice.shared.infrastructure.persistence.mappers.Mapper;
import crolopez.thecrmservice.customer.infrastructure.entities.CustomerDbEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomerDbMapper extends DbMapper<CustomerDbEntity, CustomerEntity> {

    @Override
    public CustomerEntity externalEntityToEntity(CustomerDbEntity customerDbEntity) {
        return CustomerEntity.builder()
                .id(getUnescapedValue(customerDbEntity.getId()))
                .name(getUnescapedValue(customerDbEntity.getName()))
                .surname(getUnescapedValue(customerDbEntity.getSurname()))
                .createdBy(getUnescapedValue(customerDbEntity.getCreatedBy()))
                .lastUpdateBy(getUnescapedValue(customerDbEntity.getLastUpdateBy()))
                .imageUrl(getUnescapedValue(customerDbEntity.getImageUrl()))
                .build();
    }

    @Override
    public CustomerDbEntity entityToExternalEntity(CustomerEntity customerEntity) {
        return CustomerDbEntity.builder()
                .id(getEscapedValue(customerEntity.getId()))
                .name(getEscapedValue(customerEntity.getName()))
                .surname(getEscapedValue(customerEntity.getSurname()))
                .createdBy(getEscapedValue(customerEntity.getCreatedBy()))
                .lastUpdateBy(getEscapedValue(customerEntity.getLastUpdateBy()))
                .imageUrl(getEscapedValue(customerEntity.getImageUrl()))
                .build();
    }
}
