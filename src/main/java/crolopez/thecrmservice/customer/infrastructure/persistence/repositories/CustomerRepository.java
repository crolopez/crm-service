package crolopez.thecrmservice.customer.infrastructure.persistence.repositories;

import crolopez.thecrmservice.customer.domain.entities.CustomerEntity;
import crolopez.thecrmservice.customer.infrastructure.persistence.models.CustomerDbEntity;
import crolopez.thecrmservice.shared.infrastructure.persistence.mappers.Mapper;
import crolopez.thecrmservice.shared.infrastructure.persistence.repositories.RepositoryImpl;
import crolopez.thecrmservice.shared.infrastructure.persistence.repositories.unit.UnitOfWork;
import org.springframework.stereotype.Component;

@Component
public class CustomerRepository extends RepositoryImpl<CustomerDbEntity, CustomerEntity> {
    public CustomerRepository(UnitOfWork unitOfWork, Mapper<CustomerDbEntity, CustomerEntity> mapper) {
        super(unitOfWork, mapper, CustomerDbEntity.class);
    }
}
