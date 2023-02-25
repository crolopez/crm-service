package crolopez.thecrmservice.customer.infrastructure.repositories;

import crolopez.thecrmservice.customer.domain.entities.CustomerEntity;
import crolopez.thecrmservice.customer.infrastructure.entities.CustomerDbEntity;
import crolopez.thecrmservice.shared.infrastructure.persistence.mappers.Mapper;
import crolopez.thecrmservice.shared.infrastructure.repositories.PersistenceRepositoryImpl;
import crolopez.thecrmservice.shared.infrastructure.persistence.unit.UnitOfWork;
import org.springframework.stereotype.Component;

@Component
public class CustomerRepository extends PersistenceRepositoryImpl<CustomerDbEntity, CustomerEntity> {
    public CustomerRepository(UnitOfWork unitOfWork, Mapper<CustomerDbEntity, CustomerEntity> mapper) {
        super(unitOfWork, mapper, CustomerDbEntity.class);
    }
}
