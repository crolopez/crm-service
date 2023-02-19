package crolopez.thecrmservice.shared.infrastructure.persistence.mappers;

public interface Mapper<DbEntity, Entity> {
    Entity dbEntityToEntity(DbEntity dbEntity);
    DbEntity entityToDbEntity(Entity entity);
}
