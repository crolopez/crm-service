package crolopez.thecrmservice.shared.infrastructure.persistence.unit;

import java.util.List;

public interface UnitOfWork {
    <Value, Entity> List<Entity> get(String field, Value value, Class<Entity> entityType);

    <Entity> List<Entity> get(Class<Entity> entityType);

    <Entity> void create(Entity entityClass);

    <Entity> void delete(String id, Class<Entity> entityType);

    <Entity> void update(String id, Entity entity, Class<Entity> entityType);

    <Entity> Long count(Class<Entity> entityType);}