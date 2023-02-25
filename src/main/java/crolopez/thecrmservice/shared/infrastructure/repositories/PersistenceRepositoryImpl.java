package crolopez.thecrmservice.shared.infrastructure.repositories;

import crolopez.thecrmservice.shared.application.repositories.PersistenceRepository;
import crolopez.thecrmservice.shared.infrastructure.persistence.mappers.Mapper;
import crolopez.thecrmservice.shared.infrastructure.persistence.unit.UnitOfWork;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@AllArgsConstructor
abstract public class PersistenceRepositoryImpl<PersistentEntity, Entity> implements PersistenceRepository<Entity> {

    @Autowired
    private final UnitOfWork unitOfWork;

    @Autowired
    private Mapper<PersistentEntity, Entity> mapper;

    private final Class<PersistentEntity> persistentEntityClass;

    @Override
    public Entity get(String id) {
        return get("id", id).get(0);
    }

    @Override
    public void delete(String id) {
        unitOfWork.delete(id, persistentEntityClass);
    }

    @Override
    public List<Entity> get() {
        List<PersistentEntity> dbEntities = unitOfWork.get(persistentEntityClass);
        return dbEntities.stream().map(x -> mapper.externalEntityToEntity(x)).toList();
    }

    @Override
    public Entity create(Entity customerEntity) {
        PersistentEntity persistentEntity = mapper.entityToExternalEntity(customerEntity);
        unitOfWork.create(persistentEntity);
        return customerEntity;
    }

    @Override
    public Entity update(String id, Entity customerEntity) {
        PersistentEntity persistentEntity = mapper.entityToExternalEntity(customerEntity);
        unitOfWork.update(id, persistentEntity, persistentEntityClass);
        return customerEntity;
    }

    protected <FilterType> List<Entity> get(String field, FilterType filterValue) {
        List<PersistentEntity> dbEntities = unitOfWork.get(field, filterValue, persistentEntityClass);
        return dbEntities.stream().map(x -> mapper.externalEntityToEntity(x)).toList();
    }

}