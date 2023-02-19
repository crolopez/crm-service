package crolopez.thecrmservice.shared.infrastructure.persistence.repositories;

import crolopez.thecrmservice.shared.application.repositories.Repository;
import crolopez.thecrmservice.shared.infrastructure.persistence.mappers.Mapper;
import crolopez.thecrmservice.shared.infrastructure.persistence.repositories.unit.UnitOfWork;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
abstract public class RepositoryImpl<DbEntity, Entity> implements Repository<Entity> {

    @Autowired
    private final UnitOfWork unitOfWork;

    @Autowired
    private Mapper<DbEntity, Entity> mapper;

    private final Class<DbEntity> dbEntityClass;

    @Override
    public Entity get(String id) {
        return get("id", id).get(0);
    }

    @Override
    public List<Entity> get() {
        List<DbEntity> dbEntities = unitOfWork.get(dbEntityClass);

        return dbEntities.stream().map(x -> mapper.dbEntityToEntity(x)).toList();
    }

    @Override
    public Entity create(Entity customerEntity) {
        DbEntity dbEntity = mapper.entityToDbEntity(customerEntity);
        unitOfWork.create(dbEntity);
        return customerEntity;
    }

    protected <FilterType> List<Entity> get(String field, FilterType filterValue) {
        List<DbEntity> dbEntities = unitOfWork.get(field, filterValue, dbEntityClass);

        return dbEntities.stream().map(x -> mapper.dbEntityToEntity(x)).toList();
    }

}