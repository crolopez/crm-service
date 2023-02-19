package crolopez.thecrmservice.shared.infrastructure.persistence.repositories.unit;

import java.util.List;
import java.util.UUID;

public interface UnitOfWork {
    <Value, Entity> List<Entity> get(String field, Value value, Class<Entity> entityType);

    <Entity> List<Entity> get(Class<Entity> entityType);
}